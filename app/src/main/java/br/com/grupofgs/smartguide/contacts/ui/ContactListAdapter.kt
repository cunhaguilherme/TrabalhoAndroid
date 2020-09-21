package br.com.grupofgs.smartguide.contacts.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.R.*
import br.com.grupofgs.smartguide.R.layout.contact_row
import br.com.grupofgs.smartguide.contacts.data.Contact

class ContactListAdapter internal constructor(
    context: ContactsFragment, val listener: ContactListener
): RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>(){


    private var contacts = emptyList<Contact>()



    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameItemView: TextView = itemView.findViewById(id.tvNameContact)
        val numberItemView: TextView = itemView.findViewById(id.numberContact)
        val  btDeleteContact: ImageView = itemView.findViewById(id.idDeleteContact)
        val  btEditContact: ImageView = itemView.findViewById(R.id.idEditContact)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_row, parent, false)
        return ContactViewHolder(itemView)




    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val current = contacts[position]
        holder.nameItemView.text = current.names
        holder.numberItemView.text = current.phone.toString()

        holder.btDeleteContact.setOnClickListener {
            //Navigation.createNavigateOnClickListener(R.id.action_contactsFragment_to_contactEditFragment)
            listener.delete(current)


        }

        holder.btEditContact.setOnClickListener {
            //Navigation.createNavigateOnClickListener(R.id.action_contactsFragment_to_contactEditFragment)
            //showMessage("Insert realizado com sucesso")
            listener.onEdit(current)



        }


    }

    interface ContactListener {

        fun  delete(contact: Contact)

        fun   onEdit(contact: Contact)
    }

    internal fun setContact(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override  fun getItemCount(): Int{
        return contacts.size
    }



}






