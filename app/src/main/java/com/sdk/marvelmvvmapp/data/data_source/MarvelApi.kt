package com.sdk.marvelmvvmapp.data.data_source

import com.sdk.marvelmvvmapp.data.data_source.characterDto.CharactersDTO
import com.sdk.marvelmvvmapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("ts") ts: String = Constants.timeStamp,
        @Query("hash") hash: String = Constants.hash(),
        @Query("offset") offset: String
    ): CharactersDTO
    @GET("/v1/public/characters")
    suspend fun getAllSearchedCharacters(
        @Query("apikey")apikey:String=Constants.API_KEY,
        @Query("ts")ts:String=Constants.timeStamp,
        @Query("hash")hash:String=Constants.hash(),
        @Query("nameStartsWith")search:String
    ): CharactersDTO
}