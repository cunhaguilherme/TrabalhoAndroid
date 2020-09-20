package br.com.grupofgs.smartguide.contacts.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.contacts.data.Contact
import br.com.grupofgs.smartguide.ui.base.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ContactsFragment : BaseFragment() {

    override val layout = R.layout.fragment_contacts


    private val contactViewModel: ContactViewModel by viewModels()
    private lateinit var rvContact: RecyclerView
    private lateinit var ivBack: ImageView
    private lateinit var btAddContact: FloatingActionButton
    //private lateinit var contactViewModel: ContactViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setUpView(view)
       // ContactViewModel.createMenu()

    }

    private fun setUpView(view: View) {

        rvContact = view.findViewById(R.id.rvContacts)
        val adapter = ContactListAdapter(this)
        rvContact.adapter = adapter
        rvContact.layoutManager = LinearLayoutManager(this.context)


        //contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.allWords.observe( viewLifecycleOwner, Observer { contacts ->
            contacts?.let{  adapter.setContact(it) } })


        ivBack = view.findViewById(R.id.ivBack)

        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        btAddContact = view.findViewById(R.id.btAddContact)

        btAddContact.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(
                    R.id.action_contactsFragment_to_contactAddFragment,
                    null,
                    null
                )
        }



    }









}
