package com.example.mygifapplication.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygifapplication.data.repository.GifRepositoryImpl
import com.example.mygifapplication.data.model.api.ResponseTrendingGifs
import com.example.mygifapplication.data.model.domain.Gif
import com.example.mygifapplication.data.repository.GifRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gifRepository: GifRepository,
    @ApplicationContext private val context: Context
): ViewModel() {



    //val apiRepository = ApiRepositoryImpl()
    val apiKey = "H0RH6YWpWI2HWqTs6KuLil53ofyQM2hX"

    private val _gifs = MutableLiveData<List<Gif>>()
    val gifs : LiveData<List<Gif>> get() = _gifs

    private val _gifs2 = MutableStateFlow<ResponseTrendingGifs?>(null)
    val gifs2 : StateFlow<ResponseTrendingGifs?> = _gifs2.asStateFlow()

    private val _searchText = MutableLiveData<String>()
    val searchText : LiveData<String> get() = _searchText

    private val _title = MutableLiveData<String>()
    val title : LiveData<String> get() = _title
    init {
        viewModelScope.launch {
            println("Voy a buscar gif")
            try {
                val respuestaRepo = gifRepository.getTrendingGifs(apiKey,context)
                println("Respuesta repo tiene ${respuestaRepo.size} items")
                _gifs.value = respuestaRepo
            }catch (ex: Exception){
                println("Exception: ${ex.message}")

            }


        }
    }

    fun getGiftSearch(){
        if(searchText.value?.isEmpty() == true){
            viewModelScope.launch {
                _gifs.value = gifRepository.getTrendingGifs(apiKey, context)
                _title.value = "Trending Gifs"
            }
        }else{
            viewModelScope.launch {
                _gifs.value = searchText.value?.let { gifRepository.searchGifs(apiKey, it, context) }
                _title.value = "Resultados de la búsqueda: ${searchText.value}"
            }
        }
    }

    fun setSearchText(text: String){
        _searchText.value = text
    }
}

/*
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
