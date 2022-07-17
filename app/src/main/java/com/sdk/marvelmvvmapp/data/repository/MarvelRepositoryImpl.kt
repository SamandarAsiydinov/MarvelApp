package com.sdk.marvelmvvmapp.data.repository

import com.sdk.marvelmvvmapp.data.data_source.MarvelApi
import com.sdk.marvelmvvmapp.data.data_source.characterDto.CharactersDTO
import com.sdk.marvelmvvmapp.domain.repository.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val api: MarvelApi
): MarvelRepository {
    override suspend fun getAllCharacters(offset: Int): CharactersDTO {
        return api.getAllCharacters(offset = offset.toString())
    }

    override suspend fun getAllSearchedCharacters(query: String): CharactersDTO {
        return api.getAllSearchedCharacters(search = query)
    }
}