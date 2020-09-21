package br.com.grupofgs.smartguide.contacts.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Contact_table")
class Contact(

    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "names") var names: String,
    @ColumnInfo(name = "phone") var phone: Int

) : Parcelable

