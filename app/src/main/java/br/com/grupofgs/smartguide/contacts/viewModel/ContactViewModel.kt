package br.com.grupofgs.smartguide.contacts.viewModel

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
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Contact>>

    init {
        val contactDao = ContactRoomDatabase.getDatabase(application).contactDao()
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
