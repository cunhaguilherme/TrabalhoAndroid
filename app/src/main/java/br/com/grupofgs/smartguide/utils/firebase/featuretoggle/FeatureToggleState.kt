package br.com.grupofgs.smartguide.utils.firebase.featuretoggle

enum class FeatureToggleState(val type: String) {
    INVISIBLE("INVISIBLE"),
    ENABLED("ENABLED"),
    DISABLED("DISABLED")
}