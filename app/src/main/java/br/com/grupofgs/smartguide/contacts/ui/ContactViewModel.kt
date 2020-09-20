package br.com.grupofgs.smartguide.contacts.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.grupofgs.smartguide.contacts.data.Contact
import br.com.grupofgs.smartguide.contacts.data.ContactRoomDatabase
import br.com.grupofgs.smartguide.contacts.repository.ContactRepository
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContactRepository

    val allWords: LiveData<List<Contact>>

    init {
        val contactDao = ContactRoomDatabase.getDatabase(application, viewModelScope).contactDao()
        repository = ContactRepository(contactDao)
        allWords = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(contact: Contact) = viewModelScope.launch (Dispatchers.IO) {
        repository.insert(contact)
    }
}
