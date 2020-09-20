package br.com.grupofgs.smartguide.contacts.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.R.*
import br.com.grupofgs.smartguide.R.layout.contact_row
import br.com.grupofgs.smartguide.contacts.data.Contact

class ContactListAdapter internal constructor(
    context: ContactsFragment
): RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>(){


    private var contacts = emptyList<Contact>()

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameItemView: TextView = itemView.findViewById(id.tvNameContact)
        val numberItemView: TextView = itemView.findViewById(id.numberContact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_row, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val current = contacts[position]
        holder.nameItemView.text = current.names
        holder.numberItemView.text = current.phone.toString()
    }

    internal fun setContact(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override  fun getItemCount(): Int{
        return contacts.size
    }



}




