package br.com.grupofgs.smartguide.utils.firebase.featuretoggle

data class FeatureConfig(
    val releasedVersion: Int,
    val minimumVersion: Int,
    val status: FeatureToggleState
)