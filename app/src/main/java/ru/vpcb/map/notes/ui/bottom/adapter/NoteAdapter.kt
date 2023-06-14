package ru.vpcb.map.notes.ui.bottom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.databinding.ItemCardBinding
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.utils.EMPTY_STRING
import ru.vpcb.map.notes.utils.NOTE_ADAPTER_EVEN
import ru.vpcb.map.notes.utils.NOTE_ADAPTER_ITEM


class NoteAdapter(val listener: Listener) : ListAdapter<Note, NoteAdapter.ViewHolder>(Callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.instance(parent, listener, viewType)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) NOTE_ADAPTER_EVEN
        else NOTE_ADAPTER_ITEM
    }

    class ViewHolder(val binding: ItemCardBinding, val listener: Listener, private val viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            when (viewType) {
                NOTE_ADAPTER_EVEN -> binding.card.backgroundTintList =
                    ContextCompat.getColorStateList(itemView.context, R.color.bg_card_even_tint)

                NOTE_ADAPTER_ITEM -> binding.card.backgroundTintList =
                    ContextCompat.getColorStateList(itemView.context, R.color.bg_card_tint)

                else -> {}
            }

            binding.textTitle.text = note.title ?: EMPTY_STRING
            binding.textBody.text = note.body ?: EMPTY_STRING
            binding.textCoord.text = itemView.resources.getString(
                R.string.coordinates,
                note.lat ?: 0.0, note.lon ?: 0.0
            )
            if (note.image == null) {
                binding.image.setImageResource(R.drawable.placeholder)
            } else {
                Glide.with(binding.image)
                    .load(note.image)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.image)
            }

            binding.root.setOnClickListener {
                listener.onClick(note)
            }
        }

        companion object {
            fun instance(parent: ViewGroup, listener: Listener, viewType: Int): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCardBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, listener, viewType)
            }
        }
    }

    object Callback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun onClick(note: Note)
    }
}