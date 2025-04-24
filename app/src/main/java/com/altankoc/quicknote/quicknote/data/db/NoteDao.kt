package com.altankoc.quicknote.quicknote.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.altankoc.quicknote.quicknote.data.model.Note


@Dao
interface NoteDao {

    @Query("Select * From notes Order By tarih desc")
    fun getAll(): LiveData<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("UPDATE notes SET icerik = :icerik WHERE id = :id")
    suspend fun updateNoteById(id: Int, icerik: String)
}