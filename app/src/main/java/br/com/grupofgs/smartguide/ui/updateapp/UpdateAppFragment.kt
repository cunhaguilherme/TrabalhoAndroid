package br.com.grupofgs.smartguide.ui.updateapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.ui.base.BaseFragment


class UpdateAppFragment : Fragment() {

    private lateinit var btUpdApp: Button
    private lateinit var btUpdAppLater: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
    }

    private fun setUpView(view: View) {
        btUpdApp = view.findViewById(R.id.btUpdApp)
        btUpdAppLater = view.findViewById(R.id.btUpdAppLater)
        btUpdApp.setOnClickListener {
            openAppInStore()
        }
        btUpdAppLater.setOnClickListener {
            activity?.finish()
        }
    }
    private fun openAppInStore() {
        var intent: Intent
        val packageName = activity?.packageName
        try {
            intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName"))
            startActivity(intent)
        } catch (e: android.content.ActivityNotFoundException) {
            intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
            startActivity(intent)
        }
    }

}