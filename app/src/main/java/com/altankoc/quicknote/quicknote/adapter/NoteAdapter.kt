package com.altankoc.quicknote.quicknote.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.altankoc.quicknote.databinding.RecyclerRowBinding
import com.altankoc.quicknote.quicknote.data.model.Note
import com.altankoc.quicknote.quicknote.ui.fragments.ListFragmentDirections
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.navigation.findNavController

class NoteAdapter(
    private val noteList: MutableList<Note>,
    private val onDeleteConfirmed: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    inner class NoteHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = noteList[position]
        holder.binding.textBaslik.text = currentNote.baslik
        holder.binding.textIcerik.text = currentNote.icerik
        holder.binding.textTarih.text = formatTarih(currentNote.tarih)

        holder.binding.imageButton.setOnClickListener {

            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Notu Sil")
                .setMessage("Bu notu silmek istediğinize emin misiniz?")
                .setPositiveButton("Evet") { _, _ ->
                    removeNote(currentNote)
                    onDeleteConfirmed(currentNote)
                }
                .setNegativeButton("Hayır", null)
                .show()
        }

        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(bilgiNot = currentNote)
            holder.itemView.findNavController().navigate(action)
        }


    }

    private fun formatTarih(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNoteList(newList: List<Note>) {
        noteList.clear()
        noteList.addAll(newList)
        notifyDataSetChanged()
    }


    fun removeNote(note: Note) {
        val index = noteList.indexOf(note)
        if (index != -1) {
            noteList.removeAt(index)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, noteList.size)
        }
    }
}
