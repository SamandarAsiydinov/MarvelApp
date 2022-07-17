package com.sdk.marvelmvvmapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val thumbnailExt: String,
    val comics: List<String>
): Parcelable