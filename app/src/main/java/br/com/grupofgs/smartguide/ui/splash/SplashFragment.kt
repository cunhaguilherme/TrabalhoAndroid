package br.com.grupofgs.smartguide.ui.splash

import android.os.Bundle
import android.os.Handler
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.utils.firebase.RemoteConfigUtils
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {

    private lateinit var tvSplash: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            setUpView(view)
            startSplashAnimation()

            updateRemoteConfig()
    }

    private fun setUpView(view: View){
        tvSplash = view.findViewById(R.id.tvSplash)
    }

    private fun startSplashAnimation() {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_form_login)
        tvSplash.startAnimation(anim)
    }

    private fun nextScreen(){
            val extras = FragmentNavigatorExtras(
                tvSplash to "textApp"
            )
            NavHostFragment.findNavController(this)
                .navigate(
                    R.id.action_splashFragment_to_login_graph,
                    null,
                    null,
                    extras
                )
    }


    private fun updateRemoteConfig() {
        Handler().postDelayed({
            RemoteConfigUtils.fetchAndActivate()
                .addOnCompleteListener {
                    nextScreen()
                }
        }, 2000)
    }

}