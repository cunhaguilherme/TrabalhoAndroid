package br.com.grupofgs.smartguide.ui.home

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.grupofgs.smartguide.MainActivity
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.models.dashboardmenu.DashboardItem
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dash_item.view.*

class HomeListAdapter(
    private var permissionMap: Boolean,
    private var menuItems: List<DashboardItem>,
    private var clickListener: (DashboardItem) -> Unit
) : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dash_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return menuItems.size

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(permissionMap , menuItems[position], clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(permissionMap: Boolean , item: DashboardItem, clickListener: (DashboardItem) -> Unit) {
            if(item.feature == "NAVIGATION"){
               if(!permissionMap)
                   itemView.visibility = View.INVISIBLE
            }
            itemView.tvItem.text = item.label
            GlideToVectorYou
                .init()
                .with(this.itemView.context)
                .setPlaceHolder(R.drawable.rounded_corners, R.drawable.ic_launcher_foreground)
                .load(Uri.parse(item.image), itemView.ivItem)
            itemView.setOnClickListener { clickListener(item) }
        }
    }
}