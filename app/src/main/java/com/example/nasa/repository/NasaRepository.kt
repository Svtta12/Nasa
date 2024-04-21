package com.example.nasa.repository

import androidx.lifecycle.LiveData
import com.example.nasa.api.retrofit
import com.example.nasa.model.Photo
import kotlinx.coroutines.delay

class NasaRepository {

    suspend fun getPhoto(page: Int): List<Photo> {
        delay(1000)
        return retrofit.photo(page).photos
    }

}