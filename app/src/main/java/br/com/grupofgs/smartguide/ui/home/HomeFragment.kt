package br.com.grupofgs.smartguide.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.grupofgs.smartguide.MainActivity
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.models.RequestState
import br.com.grupofgs.smartguide.models.dashboardmenu.DashboardItem
import androidx.core.os.bundleOf
import br.com.grupofgs.smartguide.extensions.startDeeplink
import br.com.grupofgs.smartguide.ui.ListenFromActivity
import br.com.grupofgs.smartguide.ui.base.BaseFragment
import br.com.grupofgs.smartguide.ui.base.auth.BaseAuthFragment
import br.com.grupofgs.smartguide.utils.SmartGuidTracker
import kotlinx.android.synthetic.main.dash_item.view.*
import java.util.*

class HomeFragment : BaseAuthFragment(), ListenFromActivity {

    override val layout = R.layout.fragment_home

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var tvHomeHelloUser: TextView
    private lateinit var rvHomeDashboard: RecyclerView
    private var permissionMap: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)

        (activity as MainActivity?)?.setActivityListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Verifica permissão de location, solicitar caso não tiver logo que abrir o app
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            //Sem permissão
            print("Não tem permissao de location, entao solicita")
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            //Com permissao
            print("Já tem permissao de location")
            this.changeMapButtonState(true)
        }

        registerBackPressedAction()
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun changeMapButtonState(mapButtonVisibility: Boolean) {
        //Retorno da Activity
        permissionMap = mapButtonVisibility

        view?.let { setUpView(it) }
        homeViewModel.createMenu(permissionMap)
        registerObserver()
    }


    private fun setUpView(view: View) {
        rvHomeDashboard = view.findViewById(R.id.rvHomeDashboard)
        tvHomeHelloUser = view.findViewById(R.id.tvHomeHelloUser)
    }

    private fun registerObserver() {

        homeViewModel.menuState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()
                    setUpMenu(it.data)
                }
                is RequestState.Error -> {
                    hideLoading()
                }
            }
        })

        homeViewModel.userNameState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    tvHomeHelloUser.text = getString(R.string.homeLoading)
                    showLoading()
                }
                is RequestState.Success -> {

                    if (Locale.getDefault().getLanguage().equals("en")) {
                        tvHomeHelloUser.text = String.format(
                            homeViewModel.dashboardMenu?.title_en ?: "",
                            it.data
                        )
                    } else {
                        tvHomeHelloUser.text = String.format(
                            homeViewModel.dashboardMenu?.title ?: "",
                            it.data
                        )
                    }

                    hideLoading()
                }
                is RequestState.Error -> {
                    tvHomeHelloUser.text = getString(R.string.homeWelcome)
                    hideLoading()
                }
            }

        })

        homeViewModel.logoutState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()
                    findNavController().navigate(R.id.login_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
            }
        })
    }

    private fun setUpMenu(items: List<DashboardItem>) {
        rvHomeDashboard.adapter = HomeListAdapter(items, this::clickItem)
    }

    private fun clickItem(item: DashboardItem) {
        item.onDisabledListener.let {
            it?.invoke(requireContext())
        }
        SmartGuidTracker.trackEvent(
            requireActivity(), bundleOf("feature" to item.feature)
        )

        if (item.onDisabledListener == null) {
            when(item.feature){
                "EXIT" -> {
                    showMessage(getString(R.string.logoutSucess))
                    homeViewModel.signOut()
                }
                "NAVIGATION" -> {
                    showMessage(item.feature)
                    NavHostFragment.findNavController(this)
                        .navigate(
                            R.id.action_homeFragment_to_mapFragment,
                            null,
                            null
                        )
                }
                "ABOUT" -> {
                    showMessage(item.feature)
                    NavHostFragment.findNavController(this)
                        .navigate(
                            R.id.action_homeFragment_to_about_nav_graph,
                            null,
                            null
                        )
                }
                "CONTACTS" -> {
                    showMessage(item.feature)
                    NavHostFragment.findNavController(this)
                        .navigate(
                            R.id.action_homeFragment_to_contactsFragment,
                            null,
                            null
                        )
                } else -> {
                    //showMessage(item.label)
                    startDeeplink(item.action.deeplink)
                }
            }

        }
    }

}




