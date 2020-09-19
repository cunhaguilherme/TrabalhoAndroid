package br.com.grupofgs.smartguide.extensions

import br.com.grupofgs.smartguide.utils.firebase.RemoteConfigUtils
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.internal.Primitives

@Throws(JsonSyntaxException::class)
fun <T> Gson.fromRemoteConfig(keyRemoteConfig: String, classOfT: Class<T>): T? {
    val json = RemoteConfigUtils.getFirebaseRemoteConfig().getString(keyRemoteConfig)
    val `object` = fromJson(json, classOfT)
    return Primitives.wrap(classOfT).cast(`object`)
}