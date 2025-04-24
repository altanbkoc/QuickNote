package com.altankoc.quicknote.quicknote.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "baslik")
    val baslik: String,

    @ColumnInfo(name = "icerik")
    val icerik: String,

    @ColumnInfo(name = "tarih")
    val tarih: Long

): Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}