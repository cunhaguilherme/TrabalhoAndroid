package br.com.grupofgs.smartguide.util

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import br.com.grupofgs.smartguide.R
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

/*object RemoteConfigUtils {
    fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.setConfigSettingsAsync(configSettings)
        return remoteConfig }
    fun fetchAndActivate(): Task<Boolean> {
        return getFirebaseRemoteConfig().fetchAndActivate()
    }
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)setUpView(view) startAnimation()
    updateRemoteConfig()
}
private fun updateRemoteConfig() { Handler().postDelayed({
    RemoteConfigUtils.fetchAndActivate() .addOnCompleteListener {
        nextScreen() }
}, 2000) }
private fun nextScreen() {
    val extras = FragmentNavigatorExtras(
        ivLogoApp to "logoApp",
        tvAppName to "textApp" )
    NavHostFragment.findNavController(this) .navigate(
        R.id.action_splashFragment_to_login_nav_graph,
        null, null, extras )
}*/
//Aula 2 p64