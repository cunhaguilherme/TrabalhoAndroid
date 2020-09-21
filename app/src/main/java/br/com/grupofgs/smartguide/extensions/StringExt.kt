package br.com.grupofgs.smartguide.extensions

import android.util.Patterns.*
import java.util.regex.Pattern

fun String.isValidEmail() = run {
    val pattern: Pattern = EMAIL_ADDRESS
    pattern.matcher(this).matches()
}