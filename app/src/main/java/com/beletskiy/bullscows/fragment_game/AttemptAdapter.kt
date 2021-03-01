package com.beletskiy.bullscows.fragment_game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beletskiy.bullscows.databinding.ItemAttemptBinding
import com.beletskiy.bullscows.game.Attempt

class AttemptAdapter : ListAdapter<Attempt, AttemptViewHolder>(AttemptDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttemptViewHolder {
        return AttemptViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AttemptViewHolder, position: Int) {
        val currentAttempt = getItem(position)
        holder.bind(currentAttempt)
    }

    override fun submitList(list: MutableList<Attempt>?) {
        super.submitList(list?.toList())
    }
}

class AttemptViewHolder(private val binding: ItemAttemptBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Attempt) {
        binding.attempt = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): AttemptViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemAttemptBinding.inflate(layoutInflater, parent, false)
            return AttemptViewHolder(binding)
        }
    }
}

class AttemptDiffItemCallback : DiffUtil.ItemCallback<Attempt>() {

    override fun areItemsTheSame(oldItem: Attempt, newItem: Attempt): Boolean {
        return oldItem.attemptNumber == newItem.attemptNumber
    }

    override fun areContentsTheSame(oldItem: Attempt, newItem: Attempt): Boolean {
        return oldItem == newItem
    }
}

