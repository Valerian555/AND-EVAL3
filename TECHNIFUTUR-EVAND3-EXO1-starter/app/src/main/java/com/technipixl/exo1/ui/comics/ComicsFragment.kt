package com.technipixl.exo1.ui.comics

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.technipixl.exo1.HashGenerator
import com.technipixl.exo1.databinding.FragmentComicsBinding
import com.technipixl.exo1.network.model.CharacterResponse
import com.technipixl.exo1.network.model.Comic
import com.technipixl.exo1.network.model.ComicsData
import com.technipixl.exo1.network.service.CharacterServiceImpl
import com.technipixl.exo1.ui.characters.CharacterAdapter
import com.technipixl.exo1.ui.characters.CharactersFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class ComicsFragment : Fragment() {

    private var binding: FragmentComicsBinding? = null
    private val args: ComicsFragmentArgs by navArgs()
    private val characterService by lazy { CharacterServiceImpl() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComicsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //paramètres récupérés
        val characterId = args.characterId
        val characterName = args.name
        val characterPath = args.path
        val characterExtension = args.extension

        //label
        binding?.characterName?.text = characterName

        //récupération de l'image
        setupImage("$characterPath.$characterExtension")

        retrieveData(characterId)
    }

    private fun setupRecyclerView(comic: ComicsData) {
        binding?.comicsRecyclerview?.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding?.comicsRecyclerview?.adapter = ComicsAdapter(comic) { comic ->
            goToDetail(comic)
        }
    }

    private fun goToDetail(comic: Comic) {
        val direction = ComicsFragmentDirections.actionComicsFragmentToComicsDetailFragment("com")
        findNavController().navigate(direction)
    }


    private fun retrieveData(characterId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = "2de9fa8103a271706e625d8e50738a9e"
            val timeStamp = Date().time
            val privateKey = "cf52b21568bf70dfeb4037711fcdf0cb44d847e0"
            val hash = HashGenerator.generateHash(timeStamp, privateKey, apiKey)
            val limit = 100
            val response = characterService.getCharacterList(
                apiKey, timeStamp, hash, limit
            ) // Récupérer la liste de personnages
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { characterResponse ->
                        val character = characterResponse.data.results.find { it.id == characterId }
                        character?.let {
                            setupRecyclerView(it.comics)
                        }
                    }
                }
            }
        }
    }

    fun setupImage(url: String) {
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into(binding?.characterImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}