package com.altankoc.quicknote.quicknote.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.findNavController
import com.altankoc.quicknote.databinding.FragmentListBinding
import com.altankoc.quicknote.quicknote.adapter.NoteAdapter
import com.altankoc.quicknote.quicknote.data.db.NoteDatabase
import com.altankoc.quicknote.quicknote.repository.NoteRepository
import com.altankoc.quicknote.quicknote.viewmodel.NoteViewModel

@Suppress("UNCHECKED_CAST")
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val dao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(dao)
        noteViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(repository) as T
            }
        })[NoteViewModel::class.java]

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteAdapter = NoteAdapter(mutableListOf()) { noteToDelete ->
            noteViewModel.deleteNote(noteToDelete)

        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }

        noteViewModel.notes.observe(viewLifecycleOwner) { noteList ->
            noteAdapter.setNoteList(noteList)
        }

        binding.floatingActionButton.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToAddFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

