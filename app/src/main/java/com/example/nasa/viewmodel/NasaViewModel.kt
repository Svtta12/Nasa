package com.example.nasa.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nasa.model.Photo
import com.example.nasa.repository.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class NasaViewModel private constructor(
    private val repository: NasaRepository
) : ViewModel() {

    constructor() : this(NasaRepository())


    val pageNasa: Flow<PagingData<Photo>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { NasaPagingSource() }
    ).flow.cachedIn(viewModelScope)


}