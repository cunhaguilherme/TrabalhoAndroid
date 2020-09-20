package br.com.grupofgs.smartguide.contacts.ui



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.ui.base.BaseFragment
import br.com.grupofgs.smartguide.ui.base.auth.BaseAuthFragment

class ContactEditFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_edit_contact

    private lateinit var ivBack: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ivBack = view.findViewById(R.id.ivBack)

        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

    }

}
