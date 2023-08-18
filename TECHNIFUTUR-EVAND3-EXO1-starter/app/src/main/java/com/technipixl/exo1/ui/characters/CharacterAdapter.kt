package com.technipixl.exo1.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.exo1.databinding.CharacterCellLayoutBinding
import com.technipixl.exo1.network.model.Character
import com.technipixl.exo1.network.model.CharacterResponse

class CharacterAdapter(
    private val characterResponse: CharacterResponse,
    private val onItemClick: (Character) -> Unit
) :
    RecyclerView.Adapter<CharacterViewHolder>() {
    private val filteredList = mutableListOf<Character>()

    init {
        filteredList.addAll(characterResponse.data.results)
    }

    //responsable de la création de chaque cellule dans la recyclerView
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        return CharacterViewHolder(
            CharacterCellLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setup(filteredList[position])
    }

    //retourne le nbr d'élément à afficher
    override fun getItemCount(): Int {
        return filteredList.size
    }
}