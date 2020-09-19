package br.com.grupofgs.smartguide.contacts.repository

import androidx.lifecycle.LiveData
import br.com.grupofgs.smartguide.contacts.dao.ContactDao
import br.com.grupofgs.smartguide.contacts.data.Contact


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ContactRepository(private val contactDao: ContactDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Contact>> = contactDao.getAlphabetizedWords()

    suspend fun insert(word: Contact) {
        contactDao.insert(word)
    }
}