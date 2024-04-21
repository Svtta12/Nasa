package com.example.nasa.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nasa.model.Photo
import com.example.nasa.repository.NasaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NasaPagingSource : PagingSource<Int, Photo>() {

    private val repository = NasaRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int = FIRST_PAGE


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {


        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            _isLoading.value = true
            repository.getPhoto(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}