package com.altankoc.quicknote.quicknote.repository

import androidx.lifecycle.LiveData
import com.altankoc.quicknote.quicknote.data.db.NoteDao
import com.altankoc.quicknote.quicknote.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): LiveData<List<Note>>{
        return noteDao.getAll()
    }

    suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun uptadeNoteById(id: Int, icerik: String){
        noteDao.updateNoteById(id,icerik)
    }
}