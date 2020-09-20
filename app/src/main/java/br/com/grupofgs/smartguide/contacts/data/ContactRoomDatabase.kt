package br.com.grupofgs.smartguide.contacts.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.grupofgs.smartguide.contacts.dao.ContactDao
import br.com.grupofgs.smartguide.contacts.ui.ContactViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Contact::class), version = 3, exportSchema = true)
public abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    private class ContactDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var contactDao = database.contactDao()

                    contactDao.deleteAll()

                    var contact = Contact(1, "Filipe Cunha", 111111111)
                    contactDao.insert(contact)



                }
            }
        }


    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): ContactRoomDatabase {
            val tempInstance = INSTANCE
            //val contactDao = ContactRoomDatabase.getDatabase(this, ContactViewModel).contactDao()
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactRoomDatabase::class.java,
                    "contact_database"
                )
                    .addCallback(ContactDatabaseCallback(scope))
                   // .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}


