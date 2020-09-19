package br.com.grupofgs.smartguide.ui.login

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.exceptions.EmailInvalidException
import br.com.grupofgs.smartguide.exceptions.PasswordInvalidException
import br.com.grupofgs.smartguide.extensions.hideKeyboard
import br.com.grupofgs.smartguide.models.RequestState
import br.com.grupofgs.smartguide.ui.base.BaseFragment

class LoginFragment : BaseFragment() {

    override val layout = R.layout.fragment_login

    private lateinit var tvUser: TextView
    private lateinit var etUser: EditText
    private lateinit var tvPassword: TextView
    private lateinit var etPassword: EditText
    private lateinit var btLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignUp: TextView

    private val loginViewModel: LoginViewModel by viewModels()

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

    private fun registerObserver() {
        this.loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Error -> showError(it.throwable)
                is RequestState.Loading -> {
                    showLoading("Realizando a autenticação")
                    hideKeyboard()
                }

            }
        })

        this.loginViewModel.resetPasswordState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage(it.data)
                }
                is RequestState.Error -> showError(it.throwable)
                is RequestState.Loading -> showLoading("Reenviando o e-mail para alteração")
            }
        })
    }

    private fun setUpView(view: View) {
        tvUser = view.findViewById(R.id.tvUser)
        etUser = view.findViewById(R.id.etUser)
        tvPassword = view.findViewById(R.id.tvPassword)
        etPassword = view.findViewById(R.id.etPassword)
        btLogin = view.findViewById(R.id.btLogin)
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword)
        tvSignUp = view.findViewById(R.id.tvSignUp)

        btLogin.setOnClickListener {
            loginViewModel.signIn(
                etUser.text.toString(),
                etPassword.text.toString()
            )
        }

        tvForgotPassword.setOnClickListener {
            loginViewModel.resetPassword(etUser.text.toString())
        }

        tvSignUp.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(
                    R.id.action_loginFragment_to_signUpFragment,
                    null,
                    null
                )
        }
    }

    private fun startLoginAnimation() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_form_login)
        tvUser.startAnimation(anim)
        etUser.startAnimation(anim)
        tvPassword.startAnimation(anim)
        etPassword.startAnimation(anim)
        btLogin.startAnimation(anim)
        tvForgotPassword.startAnimation(anim)
        tvSignUp.startAnimation(anim)
    }

    private fun showSuccess() {
        hideLoading()
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_loginFragment_to_main_nav_graph)
    }
    private fun showError(t: Throwable) {
        hideLoading()
        etUser.error = null
        etPassword.error = null
        showMessage("E-mail e/ou senha inválidos. Por favor, verifique e tente novamente.")
        when (t) {
            is EmailInvalidException -> {
                etUser.error = t.message
                etUser.requestFocus()
            }
            is PasswordInvalidException -> {
                etPassword.error = t.message
                etPassword.requestFocus()
            }
        }
    }

}