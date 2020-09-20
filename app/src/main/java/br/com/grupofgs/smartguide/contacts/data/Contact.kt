package br.com.grupofgs.smartguide.contacts.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact_table")
class Contact(

    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "names") var names: String,
    @ColumnInfo(name = "phone") var phone: Int

)

