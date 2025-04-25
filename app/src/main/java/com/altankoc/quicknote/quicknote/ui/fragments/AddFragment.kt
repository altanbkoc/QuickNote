package com.altankoc.quicknote.quicknote.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.altankoc.quicknote.databinding.FragmentAddBinding
import com.altankoc.quicknote.quicknote.data.db.NoteDatabase
import com.altankoc.quicknote.quicknote.data.model.Note
import com.altankoc.quicknote.quicknote.repository.NoteRepository
import com.altankoc.quicknote.quicknote.viewmodel.NoteViewModel
import androidx.navigation.findNavController
import com.altankoc.quicknote.quicknote.viewmodel.NoteViewModelFactory


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root


        // ViewModel başlatılıyor
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(noteDao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnAdd.setOnClickListener {
            notEkle()
        }

    }

    fun notEkle(){
        val baslik = binding.editTextBaslik.text.toString().trim()
        val icerik = binding.editTextNote.text.toString().trim()
        val tarih = System.currentTimeMillis()

        if(icerik.isEmpty() || baslik.isEmpty() ){
            Toast.makeText(requireContext(),"Lütfen boş alan bırakmayınız", Toast.LENGTH_SHORT).show()
            return
        }

        val yeniNot = Note(baslik,icerik,tarih)

        noteViewModel.insertNote(yeniNot)


        val action = AddFragmentDirections.actionAddFragmentToListFragment()
        requireView().findNavController().navigate(action)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null


    }

}