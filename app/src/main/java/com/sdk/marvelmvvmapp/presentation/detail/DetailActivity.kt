package com.sdk.marvelmvvmapp.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sdk.marvelmvvmapp.R
import com.sdk.marvelmvvmapp.databinding.ActivityDetailBinding
import com.sdk.marvelmvvmapp.domain.model.Character

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val character = intent.getParcelableExtra<Character>("char")
        character?.let {
            val imageUrl = "${
                character.thumbnail.replace(
                    "http",
                    "https"
                )
            }/portrait_xlarge.${character.thumbnailExt}"
            Glide.with(this)
                .load(imageUrl)
                .into(binding.imageView)

            binding.apply {
                textTitle.text = character.name
                textDesc.text = character.description.ifEmpty { "No Description" }
                try {
                    textCom.text = "${character.comics[0]}\n${character.comics[1]}\n${character.comics[2]}"
                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}