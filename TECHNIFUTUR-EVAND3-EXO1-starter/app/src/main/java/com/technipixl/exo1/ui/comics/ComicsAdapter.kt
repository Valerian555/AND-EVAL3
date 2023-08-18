package com.technipixl.exo1.ui.comics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.exo1.databinding.CharacterCellLayoutBinding
import com.technipixl.exo1.databinding.ComicCellLayoutBinding
import com.technipixl.exo1.network.model.Comic
import com.technipixl.exo1.network.model.ComicsData
import com.technipixl.exo1.ui.characters.CharacterViewHolder

class ComicsAdapter(
    private val comicResponse: ComicsData,
    private val onItemClick: (Comic) -> Unit
) :
    RecyclerView.Adapter<ComicViewHolder>() {
    private val filteredList = mutableListOf<Comic>()

    init {
        filteredList.addAll(comicResponse.items)
    }

    //responsable de la création de chaque cellule dans la recyclerView
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComicViewHolder {
        return ComicViewHolder(
            ComicCellLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.setup(filteredList[position])
    }

    //retourne le nbr d'élément à afficher
    override fun getItemCount(): Int {
        return filteredList.size
    }
}