package com.sdk.marvelmvvmapp.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdk.marvelmvvmapp.R
import com.sdk.marvelmvvmapp.databinding.ActivityMainBinding
import com.sdk.marvelmvvmapp.domain.model.Character
import com.sdk.marvelmvvmapp.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var searchTerm : String = ""
    var valueRepeat = 3
    var paginatedValue = 0
    var job: Job? = null
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var rLayoutManager: GridLayoutManager
    private lateinit var characterAdapter: CharacterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rLayoutManager = GridLayoutManager(this@MainActivity, 2)
        setupRv()
        binding.recyclerView.addOnScrollListener(listener)

        characterAdapter.onClick = {
            intent(it)
        }
    }

    private fun intent(character: Character) {
        val bundle = bundleOf("char" to character)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllCharactersData(paginatedValue)
        callApi()
    }

    private val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (rLayoutManager.findLastVisibleItemPosition() == rLayoutManager.itemCount -1) {
                paginatedValue += 20
                viewModel.getAllCharactersData(paginatedValue)
                callApi()
            }
        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        characterAdapter = CharacterAdapter()
        layoutManager = rLayoutManager
        adapter = characterAdapter
    }
    private fun callApi() {
        CoroutineScope(Dispatchers.Main).launch {
            repeat(valueRepeat) {
                viewModel.marvelListState.collect {
                    when(it) {
                        is MarvelListState.Init -> Unit
                        is MarvelListState.Loading -> {
                            binding.progressCircular.isVisible = true
                            binding.recyclerView.isVisible = false
                        }
                        is MarvelListState.Error -> {
                            binding.progressCircular.isVisible = false
                            binding.recyclerView.isVisible = true
                            valueRepeat = 0
                            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                        is MarvelListState.Success -> {
                            valueRepeat = 0
                            binding.progressCircular.isVisible = false
                            binding.recyclerView.isVisible = true
                            characterAdapter.submitList(it.list)
                        }
                    }
                    delay(1000)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.menuSearch)
        val searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        searchCharacter(text)
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        searchCharacter(text)
        return true
    }
    private fun searchCharacter(text: String?) {
        job?.cancel()
        job = MainScope().launch {
            delay(500L)
            text?.let { query ->
                viewModel.getAllSearchedCharacters(query.trim())
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.searchState.collect {
                        when(it) {
                            is MarvelListState.Init -> Unit
                            is MarvelListState.Loading -> {
                                binding.progressCircular.isVisible = true
                                binding.recyclerView.isVisible = false
                            }
                            is MarvelListState.Error -> {
                                binding.progressCircular.isVisible = false
                                binding.recyclerView.isVisible = true
                            }
                            is MarvelListState.Success -> {
                                binding.progressCircular.isVisible = false
                                binding.recyclerView.isVisible = true
                                characterAdapter.submitList(it.list)
                            }
                        }
                    }
                }
            }
        }
    }
}