package br.com.grupofgs.smartguide.contacts.ui



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.viewModels
import br.com.concrete.canarinho.watcher.TelefoneTextWatcher
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.contacts.data.Contact
import br.com.grupofgs.smartguide.ui.base.BaseFragment
import br.com.grupofgs.smartguide.ui.base.auth.BaseAuthFragment
import br.com.grupofgs.smartguide.utils.MaskEditUtil
import com.airbnb.lottie.model.content.Mask

class ContactAddFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_add_contact

    private val contactViewModel: ContactViewModel by viewModels()

    private lateinit var ivBack: ImageView

    private lateinit var etNameUser: EditText
    private lateinit var etPhoneUser: EditText
    private lateinit var btAddUser: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)


    }

    private fun setUpView(view: View) {

        etNameUser = view.findViewById(R.id.etNameUser)
        etPhoneUser = view.findViewById(R.id.etPhoneUser)
        btAddUser = view.findViewById<Button>(R.id.btAddContact)

        etPhoneUser.addTextChangedListener(MaskEditUtil.mask(etPhoneUser, MaskEditUtil.FORMAT_CELLFONE));


        btAddUser.setOnClickListener{

            if(TextUtils.isEmpty(etNameUser.text) || TextUtils.isEmpty(etPhoneUser.text)){
                showMessage("Nao foi possivel realizar o insert")
            }else{

                val name = etNameUser.text.toString()
                val phone = etPhoneUser.text.toString()
                    .replace("-", "")
                    .replace(" ", "")
                var contact : Contact =  Contact(0, name, phone.toInt() )

                contactViewModel.insert(contact)



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