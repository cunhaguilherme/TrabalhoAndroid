package br.com.grupofgs.smartguide.models

import com.google.firebase.firestore.Exclude

data class NewUser(
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @Exclude val terms: Boolean? = false,
    @Exclude val password: String? = null
)