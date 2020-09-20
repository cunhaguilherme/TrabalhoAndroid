package br.com.grupofgs.smartguide.contacts.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.grupofgs.smartguide.contacts.data.Contact

@Dao
interface ContactDao {

    @Query("SELECT * from contact_table ORDER BY names ASC")
    fun getAlphabetizedWords(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(names: Contact)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()
}