package br.com.grupofgs.smartguide.models.dashboardmenu

import android.content.Context

data class DashboardMenu(
    val title: String,
    val subTitle: String,
    val items: List<DashboardItem>
)

data class DashboardItem(
    val feature: String,
    val image: String,
    val label: String,
    val label_en: String,
    val action: DashboardAction,
    var onDisabledListener: ((Context) -> Unit)?
)

data class DashboardAction(
    val deeplink: String,
    val params: HashMap<String, Any>
)