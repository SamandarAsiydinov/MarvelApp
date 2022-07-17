package com.sdk.marvelmvvmapp.data.data_source.characterDto

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)