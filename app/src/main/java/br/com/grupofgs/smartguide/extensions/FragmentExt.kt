package br.com.grupofgs.smartguide.extensions

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import br.com.gabrielandrepiva.smarguidelib.hideKeyboard
import br.com.gabrielandrepiva.smarguidelib.showKeyboard

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it)
    }
}
fun Fragment.showKeyboard() {
    view?.let { activity?.showKeyboard()
    }
}
fun Fragment.startDeeplink(deeplink: String) {
    if (deeplink.contains("://")) {
        val uri = Uri.parse(deeplink)
        val responseIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(responseIntent)
    } else {
        val internalIntent = Intent(deeplink)
        startActivity(internalIntent)
    }
}