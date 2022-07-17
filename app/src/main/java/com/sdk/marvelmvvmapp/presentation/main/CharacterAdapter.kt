package com.sdk.marvelmvvmapp.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sdk.marvelmvvmapp.databinding.ItemLayoutBinding
import com.sdk.marvelmvvmapp.domain.model.Character

class CharacterAdapter : ListAdapter<Character, CharacterAdapter.CharacterViewHolder>(DiffCallBack()) {

    lateinit var onClick: (Character) -> Unit

    private class DiffCallBack: DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharacterViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                val imageUrl = "${character.thumbnail.replace("http", "https")}/portrait_xlarge.${character.thumbnailExt}"
                txtCharacterName.text = character.name
                Glide.with(imgCharacterImage)
                    .load(imageUrl)
                    .into(imgCharacterImage)
            }
            itemView.setOnClickListener {
                onClick.invoke(character)
            }
        }
    }
}