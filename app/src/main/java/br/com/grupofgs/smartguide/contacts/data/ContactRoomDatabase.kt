package br.com.grupofgs.smartguide.contacts.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.grupofgs.smartguide.contacts.dao.ContactDao

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
public abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(context: Context): ContactRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactRoomDatabase::class.java,
                    "contact_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}