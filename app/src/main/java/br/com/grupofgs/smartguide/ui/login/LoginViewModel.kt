package br.com.grupofgs.smartguide.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.SmartGuidApplication
import br.com.grupofgs.smartguide.exceptions.EmailInvalidException
import br.com.grupofgs.smartguide.exceptions.PasswordInvalidException
import br.com.gabrielandrepiva.smarguidelib.isValidEmail
import br.com.grupofgs.smartguide.models.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val loginState = MutableLiveData<RequestState<FirebaseUser>>()

    fun signIn(email: String, password: String) {

        loginState.value = RequestState.Loading

        if (validateFields(email, password)) {
            mAuth.signInWithEmailAndPassword(
                email,
                password
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loginState.value = RequestState.Success(mAuth.currentUser!!)
                    } else {
                        loginState.value = RequestState.Error(
                            Throwable(
                                task.exception?.message ?: SmartGuidApplication.context?.resources?.getString(R.string.loginErrorRequisition)
                            )
                        )
                    }
                }
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        if (!email.isValidEmail()) {
            loginState.value = RequestState.Error(EmailInvalidException())
            return false
        }
        if (password.isEmpty()) {
            loginState.value = RequestState.Error(PasswordInvalidException(SmartGuidApplication.context?.resources?.getString(R.string.loginErrorPwdEmpty)!!))
            return false
        }
        if (email.length < 6) {
            loginState.value =
                RequestState.Error(PasswordInvalidException(SmartGuidApplication.context?.resources?.getString(R.string.loginErrorPwdLength)!!))
            return false
        }
        return true
    }

    val resetPasswordState = MutableLiveData<RequestState<String>>()

    fun resetPassword(email: String) {
        resetPasswordState.value = RequestState.Loading
        if(email.isValidEmail()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        resetPasswordState.value = RequestState.Success(SmartGuidApplication.context?.resources?.getString(R.string.loginPwdCheckEmail)!!)
                    } else {
                        resetPasswordState.value = RequestState.Error(
                            Throwable(
                                task.exception?.message ?: SmartGuidApplication.context?.resources?.getString(R.string.loginErrorRequisition)
                            )
                        )
                    }
                }
        } else {
            resetPasswordState.value = RequestState.Error(EmailInvalidException())
        }
    }

}