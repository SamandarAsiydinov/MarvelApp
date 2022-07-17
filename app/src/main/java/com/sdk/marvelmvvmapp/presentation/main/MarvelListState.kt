package com.sdk.marvelmvvmapp.presentation.main

import com.sdk.marvelmvvmapp.domain.model.Character

sealed class MarvelListState {
    object Init : MarvelListState()
    object Loading : MarvelListState()
    data class Error(val message: String) : MarvelListState()
    data class Success(val list: List<Character>) : MarvelListState()

}