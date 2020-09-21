package br.com.grupofgs.smartguide.contacts.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.grupofgs.smartguide.contacts.data.Contact

@Dao
interface ContactDao {

    @Query("SELECT * from contact_table ORDER BY names ASC")
    fun getAlphabetizedWords(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(names: Contact)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)



    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()
}