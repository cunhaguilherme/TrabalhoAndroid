package br.com.grupofgs.smartguide.ui.home

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.models.RequestState
import br.com.grupofgs.smartguide.ui.base.BaseFragment
import com.crashlytics.android.Crashlytics

class HomeFragment : BaseFragment() {

    override val layout = R.layout.fragment_home


    private lateinit var btMaps: Button
    private lateinit var btContacts: Button
    private lateinit var btAbout: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        startLoginAnimation()
        registerObserver()
    }

    private fun setUpView(view: View) {
        btMaps = view.findViewById(R.id.btMaps)
        btContacts = view.findViewById(R.id.btContacts)
        btAbout = view.findViewById(R.id.btAbout)


        btAbout.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(
                    R.id.action_homeFragment_to_about_nav_graph,
                    null,
                    null
                )
        }


        }

    private fun startLoginAnimation() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_form_login)
        btMaps.startAnimation(anim)
        btContacts.startAnimation(anim)
        btAbout.startAnimation(anim)
    }

    private fun registerObserver() {
//        this.loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is RequestState.Success -> showSuccess()
//                is RequestState.Error -> showError(it.throwable)
//                is RequestState.Loading -> showLoading("Realizando a autenticação")
//            }
//        })

//    this.loginViewModel.resetPasswordState.observe(viewLifecycleOwner, Observer {
//        when (it) {
//            is RequestState.Success -> {
//                hideLoading()
//                showMessage(it.data)
//            }
//            is RequestState.Error -> showError(it.throwable)
//            is RequestState.Loading -> showLoading("Reenviando o e-mail para alteração")
//        }
//    })

    }

}



