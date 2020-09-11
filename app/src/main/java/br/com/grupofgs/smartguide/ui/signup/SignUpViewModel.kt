package br.com.grupofgs.smartguide.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.concrete.canarinho.validator.ValidadorTelefone
import br.com.grupofgs.smartguide.exceptions.EmailInvalidException
import br.com.grupofgs.smartguide.exceptions.PasswordInvalidException
import br.com.grupofgs.smartguide.extensions.isValidEmail
import br.com.grupofgs.smartguide.models.NewUser
import br.com.grupofgs.smartguide.models.RequestState
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

class SignUpViewModel : ViewModel() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    val signUpState = MutableLiveData<RequestState<FirebaseUser>>()

    fun signUp(newUser: NewUser) {

        signUpState.value = RequestState.Loading

        if (validateFields(newUser)) {
            mAuth.createUserWithEmailAndPassword(
                newUser.email ?: "",
                newUser.password ?: ""
            )
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                saveInFirestore(newUser)
                    sendEmailVerification()
            } else {
                    signUpState.value = RequestState.Error(
                        Throwable(
                            task.exception?.message ?: "Não foi possível realizar a requisição"
                        )
                    )
                }
            }
        }
    }

    private fun saveInFirestore(newUser: NewUser) {
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .set(newUser)
            .addOnSuccessListener { documentReference ->
                sendEmailVerification()
            }
            .addOnFailureListener { e ->
                signUpState.value = RequestState.Error(
                    Throwable(e.message)
                )
            }
    }

    private fun sendEmailVerification() {
        mAuth.currentUser?.sendEmailVerification()
        ?.addOnCompleteListener { task ->
            signUpState.value = RequestState.Success(mAuth.currentUser!!)
        }
    }

    private fun validateFields(newUser: NewUser): Boolean {

        if (newUser.username?.isEmpty() == true) {
            signUpState.value = RequestState.Error(Throwable("Informe o nome do usuário"))
            return false
        }

        if (newUser.email?.isValidEmail() == false) {
            signUpState.value = RequestState.Error(EmailInvalidException())
            return false
        }

        if (newUser.phone?.isEmpty() == true) {
            signUpState.value = RequestState.Error(Throwable("Informe o telefone do usuário"))
            return false
        }

        if (!ValidadorTelefone.TELEFONE.ehValido(newUser.phone)) {
            signUpState.value = RequestState.Error(Throwable("Informe um telefone válido"))
            return false
        }

        if (newUser.password?.isEmpty() == true) {
            signUpState.value = RequestState.Error(PasswordInvalidException("Informe uma senha"))
            return false
        }

        if (newUser.password?.length ?: 0 < 6) {
            signUpState.value = RequestState.Error(PasswordInvalidException("Senha com no mínimo 6 caracteres"))
            return false
        }

        if (newUser.terms == false) {
            signUpState.value = RequestState.Error(Throwable("Os termos de uso devem ser aceitos"))
            return false
        }

        return true
    }
}