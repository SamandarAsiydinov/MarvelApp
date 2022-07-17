package com.sdk.marvelmvvmapp.mappers

import com.sdk.marvelmvvmapp.data.data_source.characterDto.Result
import com.sdk.marvelmvvmapp.domain.model.Character

fun Result.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.path,
        thumbnailExt = thumbnail.extension,
        comics = comics.items.map {
            it.name
        }
    )
}