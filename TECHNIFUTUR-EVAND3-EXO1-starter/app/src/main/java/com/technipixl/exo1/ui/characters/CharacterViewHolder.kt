package com.technipixl.exo1.ui.characters

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.exo1.databinding.CharacterCellLayoutBinding
import com.technipixl.exo1.network.model.Character

class CharacterViewHolder(
    private var viewBinding: CharacterCellLayoutBinding,
    private val onItemClick: (Character) -> Unit
) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun setup(character: Character) {
        //Label
        viewBinding.characterName.text = character.name

        //image
        setupImage("${character.thumbnail.path}.${character.thumbnail.extension}")

        viewBinding.container.setOnClickListener {
            onItemClick(character)
        }
    }

    private fun setupImage(url: String) {
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into(viewBinding.characterImage)
    }
}