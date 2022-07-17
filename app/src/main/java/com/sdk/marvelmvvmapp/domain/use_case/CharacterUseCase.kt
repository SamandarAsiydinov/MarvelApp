package com.sdk.marvelmvvmapp.domain.use_case

import com.sdk.marvelmvvmapp.domain.model.Character
import com.sdk.marvelmvvmapp.domain.repository.MarvelRepository
import com.sdk.marvelmvvmapp.mappers.toCharacter
import com.sdk.marvelmvvmapp.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val repository: MarvelRepository
) {
    operator fun invoke(offset: Int): Flow<Response<List<Character>>> = flow {
        try {
            emit(Response.Loading())
            val list = repository.getAllCharacters(offset).data.results.map {
                it.toCharacter()
            }
            emit(Response.Success(list))
        } catch (e: HttpException) {
            emit(Response.Error(e.stackTraceToString()))
        } catch (e: IOException) {
            emit(Response.Error(e.stackTraceToString()))
        }
    }
}