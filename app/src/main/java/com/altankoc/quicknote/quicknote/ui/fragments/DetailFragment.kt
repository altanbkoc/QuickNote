package com.altankoc.quicknote.quicknote.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.altankoc.quicknote.R
import com.altankoc.quicknote.databinding.FragmentDetailBinding
import com.altankoc.quicknote.quicknote.data.db.NoteDatabase
import com.altankoc.quicknote.quicknote.repository.NoteRepository
import com.altankoc.quicknote.quicknote.viewmodel.NoteViewModel
import com.altankoc.quicknote.quicknote.viewmodel.NoteViewModelFactory


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var noteViewModel: NoteViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
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

        val note = args.bilgiNot

        binding.textNotBaslik.text = note.baslik
        binding.editTextNote2.setText(note.icerik)


        binding.btnUpdate.setOnClickListener {
            val guncelText = binding.editTextNote2.text.toString().trim()

            if(guncelText.isEmpty()){
                Toast.makeText(requireContext(),"Lütfen boş alan bırakmayınız", Toast.LENGTH_SHORT).show()

            }else{
                noteViewModel.updateNoteById(note.id,guncelText)

                val action = DetailFragmentDirections.actionDetailFragmentToListFragment()
                requireView().findNavController().navigate(action)

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}