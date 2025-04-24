package com.altankoc.quicknote.quicknote.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altankoc.quicknote.quicknote.data.model.Note
import com.altankoc.quicknote.quicknote.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes: LiveData<List<Note>> = repository.getAllNotes()

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun updateNoteById(id: Int, icerik: String){
        viewModelScope.launch {
            repository.uptadeNoteById(id,icerik)
        }
    }


//    fun updateNote(note: Note) {
//        Log.d("NoteViewModel", "Güncelleniyor: ${note.baslik}")
//        viewModelScope.launch {
//            try {
//                repository.updateNote(note)
//                Log.d("NoteViewModel", "Güncelleme başarılı")
//            } catch (e: Exception) {
//                Log.e("NoteViewModel", "Güncellenemedi: ${e.message}")
//            }
//        }
//    }
}
