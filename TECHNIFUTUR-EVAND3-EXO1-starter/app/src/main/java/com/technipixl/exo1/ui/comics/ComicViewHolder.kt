package com.technipixl.exo1.ui.comics

import androidx.recyclerview.widget.RecyclerView
import com.technipixl.exo1.databinding.ComicCellLayoutBinding
import com.technipixl.exo1.network.model.Comic

class ComicViewHolder (private var viewBinding: ComicCellLayoutBinding,
                       private val onItemClick: (Comic) -> Unit) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun setup(comic: Comic) {
        viewBinding.comicName.text = comic.name

        viewBinding.container.setOnClickListener {
            onItemClick(comic)
        }
    }
}