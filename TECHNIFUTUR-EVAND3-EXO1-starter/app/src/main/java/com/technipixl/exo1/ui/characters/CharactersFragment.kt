package com.technipixl.exo1.ui.characters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.exo1.HashGenerator
import com.technipixl.exo1.databinding.FragmentCharactersBinding
import com.technipixl.exo1.network.model.Character
import com.technipixl.exo1.network.model.CharacterResponse
import com.technipixl.exo1.network.service.CharacterServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class CharactersFragment : Fragment() {

    private var binding: FragmentCharactersBinding? = null
    private val characterService by lazy { CharacterServiceImpl() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        retrieveData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupRecyclerView(characterResponse: CharacterResponse) {
        binding?.characterRecyclerview?.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        binding?.characterRecyclerview?.adapter = CharacterAdapter(characterResponse) { character ->
            goToDetail(character)
        }
    }

    private fun goToDetail(character: Character) {
        val direction = CharactersFragmentDirections.actionCharactersFragmentToComicsFragment(
            character.id,
            character.name, character.thumbnail.path, character.thumbnail.extension
        )
        findNavController().navigate(direction)
    }

    private fun retrieveData() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = "2de9fa8103a271706e625d8e50738a9e"
            val timeStamp = Date().time
            val privateKey = "cf52b21568bf70dfeb4037711fcdf0cb44d847e0"
            val hash = HashGenerator.generateHash(timeStamp, privateKey, apiKey)
            val limit = 100

            val response = characterService.getCharacterList(
                apiKey, timeStamp, hash, limit
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val errorBody = response.errorBody()?.string()
                    Log.d("false", "$errorBody")
                    response.body()?.let { body ->
                        setupRecyclerView(characterResponse = body)
                    }
                }
            }
        }
    }
}
