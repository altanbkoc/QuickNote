package com.altankoc.quicknote.quicknote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.altankoc.quicknote.quicknote.repository.NoteRepository

class NoteViewModelFactory(private val repository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(repository) as T
        }
        throw IllegalStateException("ViewModel bulunamadı")
    }
}