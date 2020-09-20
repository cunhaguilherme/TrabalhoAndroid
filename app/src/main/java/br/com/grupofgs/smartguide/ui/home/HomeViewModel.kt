package br.com.grupofgs.smartguide.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.grupofgs.smartguide.extensions.fromRemoteConfig
import br.com.grupofgs.smartguide.models.RequestState
import br.com.grupofgs.smartguide.models.dashboardmenu.DashboardItem
import br.com.grupofgs.smartguide.models.dashboardmenu.DashboardMenu
import br.com.grupofgs.smartguide.utils.firebase.RemoteConfigUtils
import br.com.grupofgs.smartguide.utils.firebase.constants.RemoteConfigKeys
import br.com.grupofgs.smartguide.utils.firebase.featuretoggle.FeatureToggleHelper
import br.com.grupofgs.smartguide.utils.firebase.featuretoggle.FeatureToggleListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class HomeViewModel : ViewModel() {
    val menuState = MutableLiveData<RequestState<List<DashboardItem>>>()

    private val db = FirebaseFirestore.getInstance()
    var dashboardMenu: DashboardMenu? = null
    val userNameState = MutableLiveData<RequestState<String>>()

    val logoutState = MutableLiveData<RequestState<String>>()

    private fun getUser() {
        userNameState.value = RequestState.Loading

        val user = FirebaseAuth.getInstance().uid

        if (user == null) {
            userNameState.value = RequestState.Error(Throwable("Usuário deslogado"))
        } else {
            db.collection("users")
                .document(FirebaseAuth.getInstance().uid ?: "")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val userName = documentSnapshot.data?.get("username") as String
                    userNameState.value = RequestState.Success(userName)
                }
                .addOnFailureListener {
                    userNameState.value = RequestState.Error(it)
                }
        }
    }

    fun createMenu(permissionMap: Boolean) {

        menuState.value = RequestState.Loading

        RemoteConfigUtils.fetchAndActivate()
            .addOnCompleteListener {
                dashboardMenu =
                    Gson().fromRemoteConfig(
                        RemoteConfigKeys.MENU_DASHBOARD,
                        DashboardMenu::class.java
                    )

                getUser()

                val dashBoardItems = arrayListOf<DashboardItem>()
                val itemsMenuOriginal = dashboardMenu?.items ?: listOf()
                var itemsMenu: MutableList<DashboardItem> = itemsMenuOriginal.toMutableList()

                //Remove MapButton se nao tiver permissão
                if (!permissionMap) {
                    itemsMenu.removeAt(0)
                }

                for (itemMenu in itemsMenu) {
                    FeatureToggleHelper().configureFeature(
                        itemMenu.feature,
                        object : FeatureToggleListener {
                            override fun onEnabled() {
                                dashBoardItems.add(itemMenu)
                            }
                            override fun onInvisible() {}
                            override fun onDisabled(clickListener: (Context) -> Unit) {
                                itemMenu.onDisabledListener = clickListener
                                dashBoardItems.add(itemMenu)
                            }
                        }
                    )
                }

                menuState.value = RequestState.Success(dashBoardItems)
            }
    }

    fun signOut() {
        logoutState.value = RequestState.Loading
        FirebaseAuth.getInstance().signOut()
        logoutState.value = RequestState.Success("")
    }



}
