package br.com.grupofgs.smartguide.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import br.com.concrete.canarinho.watcher.TelefoneTextWatcher
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.extensions.hideKeyboard
import br.com.grupofgs.smartguide.models.RequestState
import br.com.grupofgs.smartguide.ui.base.BaseFragment
import com.airbnb.lottie.LottieAnimationView
import br.com.gabrielandrepiva.smarguidelib.NewUser

class SignUpFragment : BaseFragment() {
    override val layout = R.layout.fragment_sign_up

    private val signUpViewModel: SignUpViewModel by viewModels()

    private lateinit var etUserNameSignUp: EditText
    private lateinit var etEmailSignUp: EditText
    private lateinit var etPhoneSignUp: EditText
    private lateinit var etPasswordSignUp: EditText
    private lateinit var cbTermsSignUp: LottieAnimationView
    private lateinit var tvTerms: TextView
    private lateinit var btCreateAccount: Button
    //private lateinit var btLoginSignUp: TextView
    private var checkBoxDone = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)

        registerObserver()
    }

    private fun setUpView(view: View) {

        etUserNameSignUp = view.findViewById(R.id.etNameUser)
        etEmailSignUp = view.findViewById(R.id.etEmailUser)
        etPhoneSignUp = view.findViewById(R.id.etPhoneUser)
        etPasswordSignUp = view.findViewById(R.id.etPasswordUser)
        cbTermsSignUp = view.findViewById(R.id.cbTerms)
        tvTerms = view.findViewById(R.id.tvTerms)
        btCreateAccount = view.findViewById(R.id.btSignUp)
        setUpListener()
    }

    private fun setUpListener() {

        etPhoneSignUp.addTextChangedListener(TelefoneTextWatcher(object : EventoDeValidacao {
            override fun totalmenteValido(valorAtual: String?) {}
            override fun invalido(valorAtual: String?, mensagem: String?) {}
            override fun parcialmenteValido(valorAtual: String?) {}
        }))

        tvTerms.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_signUpFragment_to_termsFragment)
        }

        btCreateAccount.setOnClickListener {
            hideKeyboard()
            val newUser = NewUser(
                etUserNameSignUp.text.toString(),
                etEmailSignUp.text.toString(),
                etPhoneSignUp.text.toString(),
                checkBoxDone,
                etPasswordSignUp.text.toString()
            )
            signUpViewModel.signUp(
                newUser
            )
        }
        setUpCheckboxListener()
    }

    private fun setUpCheckboxListener() {
        cbTermsSignUp.setOnClickListener {
            if (checkBoxDone) {
                cbTermsSignUp.speed = -1f
                cbTermsSignUp.playAnimation()
                checkBoxDone = false
        } else {
                cbTermsSignUp.speed = 1f
                cbTermsSignUp.playAnimation()
                checkBoxDone = true
            }
        }
    }

    private fun registerObserver() {
        this.signUpViewModel.signUpState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    Toast.makeText(this.context, getString(R.string.signUpSucess), Toast.LENGTH_SHORT).show()
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.main_nav_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> showLoading(getString(R.string.signUpAction))
            }
        })
    }
}
