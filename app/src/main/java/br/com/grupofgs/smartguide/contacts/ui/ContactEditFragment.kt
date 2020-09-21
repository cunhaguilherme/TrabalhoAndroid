package br.com.grupofgs.smartguide.contacts.ui



import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.viewModels
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.contacts.data.Contact
import br.com.grupofgs.smartguide.ui.base.BaseFragment
import br.com.grupofgs.smartguide.ui.base.auth.BaseAuthFragment

class ContactEditFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_edit_contact

    private lateinit var ivBack: ImageView
    private lateinit var btAddUser: Button
    private lateinit var contact: Contact

    private val contactViewModel: ContactViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contact = arguments?.getParcelable<Contact>("contact") as Contact
        etNameUser.setText(contact.names)
        etPhoneUser.setText(contact.phone.toString())


        setUpView(view)



    }

    private fun setUpView(view: View) {


        btAddUser = view.findViewById<Button>(R.id.btAddContact)
        btAddUser.setOnClickListener{

            if(TextUtils.isEmpty(etNameUser.text) || TextUtils.isEmpty(etPhoneUser.text)){
                showMessage("Preencha as informações corretamente!")
            }else{



                val name = etNameUser.text.toString()
                val phone = etPhoneUser.text.toString()
                contact.names = name
                contact.phone = phone.toInt()

                contactViewModel.update(contact)



                showMessage("Insert realizado com sucesso")
            }
            activity?.onBackPressed()
        }

        ivBack = view.findViewById(R.id.ivBack)

        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

    }

}
