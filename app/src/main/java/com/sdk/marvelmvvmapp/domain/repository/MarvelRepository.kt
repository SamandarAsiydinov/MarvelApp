package com.sdk.marvelmvvmapp.domain.repository

import com.sdk.marvelmvvmapp.data.data_source.characterDto.CharactersDTO

interface MarvelRepository {
    suspend fun getAllCharacters(offset: Int): CharactersDTO
    suspend fun getAllSearchedCharacters(query: String): CharactersDTO
}