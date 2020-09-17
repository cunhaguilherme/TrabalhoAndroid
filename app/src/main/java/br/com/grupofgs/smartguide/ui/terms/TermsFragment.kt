package br.com.grupofgs.smartguide.ui.terms

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.ui.base.BaseFragment


class TermsFragment : BaseFragment() {

    override val layout = R.layout.fragment_terms

    private lateinit var wvTerms: WebView
    private lateinit var ivBack: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wvTerms = view.findViewById(R.id.wvLicenseTerms)
        ivBack = view.findViewById(R.id.ivBack)

        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        wvTerms.loadUrl("https://smartguideapp-b4f8c.web.app/")
    }

}
