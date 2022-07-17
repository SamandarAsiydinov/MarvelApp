package com.sdk.marvelmvvmapp.data.data_source.characterDto

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)