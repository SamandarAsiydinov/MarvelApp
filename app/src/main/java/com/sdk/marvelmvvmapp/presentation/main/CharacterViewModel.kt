package com.sdk.marvelmvvmapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.marvelmvvmapp.domain.use_case.CharacterUseCase
import com.sdk.marvelmvvmapp.domain.use_case.SearchCharacterUseCase
import com.sdk.marvelmvvmapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val searchCharacterUseCase: SearchCharacterUseCase
) : ViewModel() {
    private val _marvelListState: MutableStateFlow<MarvelListState> = MutableStateFlow(MarvelListState.Init)
    val marvelListState: StateFlow<MarvelListState> get() = _marvelListState
    private val _searchState: MutableStateFlow<MarvelListState> = MutableStateFlow(MarvelListState.Init)
    val searchState: StateFlow<MarvelListState> get() = _searchState

    fun getAllCharactersData(offset: Int) = viewModelScope.launch(Dispatchers.IO) {
        characterUseCase.invoke(offset).collect {
            when(it) {
                is Response.Loading -> {
                    _marvelListState.value = MarvelListState.Loading
                }
                is Response.Error -> {
                    _marvelListState.value = MarvelListState.Error(it.message.toString())
                }
                is Response.Success -> {
                    _marvelListState.value = MarvelListState.Success(it.data ?: emptyList())
                }
            }
        }
    }
    fun getAllSearchedCharacters(query: String) = viewModelScope.launch(Dispatchers.IO) {
        searchCharacterUseCase.invoke(query).collect {
            when(it) {
                is Response.Loading -> {
                    _searchState.value = MarvelListState.Loading
                }
                is Response.Error -> {
                    _searchState.value = MarvelListState.Error(it.message.toString())
                }
                is Response.Success -> {
                    _searchState.value = MarvelListState.Success(it.data ?: emptyList())
                }
            }
        }
    }
}