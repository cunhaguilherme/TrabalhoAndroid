package br.com.grupofgs.smartguide.ui.home

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.models.RequestState
import br.com.grupofgs.smartguide.models.dashboardmenu.DashboardItem
import br.com.grupofgs.smartguide.ui.base.BaseFragment

class HomeFragment : BaseFragment() {

    override val layout = R.layout.fragment_home

    //private lateinit var btMaps: Button
    //private lateinit var btContacts: Button
    //private lateinit var btAbout: Button

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var tvHomeHelloUser: TextView
    private lateinit var rvHomeDashboard: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        homeViewModel.createMenu()
        //startLoginAnimation()
        registerObserver()
    }

    private fun setUpView(view: View) {
        //btMaps = view.findViewById(R.id.btMaps)
        //btContacts = view.findViewById(R.id.btContacts)
        //btAbout = view.findViewById(R.id.btAbout)
        rvHomeDashboard = view.findViewById(R.id.rvHomeDashboard)
        tvHomeHelloUser = view.findViewById(R.id.tvHomeHelloUser)

        /*btAbout.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(
                    R.id.action_homeFragment_to_about_nav_graph,
                    null,
                    null
                )
        }*/
    }


    /*private fun startLoginAnimation() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_form_login)
        btMaps.startAnimation(anim)
        btContacts.startAnimation(anim)
        btAbout.startAnimation(anim)
    }*/

    private fun registerObserver() {

        homeViewModel.menuState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading() }
                is RequestState.Success -> {
                    hideLoading()
                    setUpMenu(it.data)
                }
                is RequestState.Error -> {
                    hideLoading() }
            }
        })

        homeViewModel.userNameState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> { tvHomeHelloUser.text = "Carregando ..."
                    showLoading()
                }
                is RequestState.Success -> {
                    tvHomeHelloUser.text = String.format(homeViewModel.dashboardMenu?.title ?: "", it.data)
                    hideLoading()
                }
                is RequestState.Error -> {
                    tvHomeHelloUser.text = "Bem-vindo"
                    hideLoading()
                }
            }

        })

        homeViewModel.logoutState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading() }
                is RequestState.Success -> {
                    hideLoading()
                    findNavController().navigate(R.id.login_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message) }
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

        if (item.onDisabledListener == null) {
            when(item.feature){
                "EXIT" -> {
                    showMessage("Logout efetuado com sucesso")
                    homeViewModel.signOut()
                }
                "NAVIGATION" -> {
                    showMessage(item.feature)
                    NavHostFragment.findNavController(this)
                        .navigate(
                            R.id.action_homeFragment_to_about_nav_graph,
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
                            R.id.action_homeFragment_to_about_nav_graph,
                            null,
                            null
                        )
                } else -> {
                    showMessage(item.label)
                }
            }



        }
    }
}





