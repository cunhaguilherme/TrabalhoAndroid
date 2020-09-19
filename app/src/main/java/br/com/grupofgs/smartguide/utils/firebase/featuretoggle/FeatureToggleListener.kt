package br.com.grupofgs.smartguide.utils.firebase.featuretoggle

import android.content.Context

interface FeatureToggleListener {
    fun onEnabled()
    fun onInvisible()
    fun onDisabled(clickListener: (Context) -> Unit)
}