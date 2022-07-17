package com.sdk.marvelmvvmapp.data.data_source.characterDto

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)