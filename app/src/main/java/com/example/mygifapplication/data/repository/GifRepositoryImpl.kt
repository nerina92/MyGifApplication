package com.example.mygifapplication.data.repository

import com.example.mygifapplication.data.model.domain.Gif
import javax.inject.Inject
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.mygifapplication.data.apiToDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar

class GifRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteApiDataSource: RemoteApiDataSource,
) : GifRepository {
    //val service = ApiServiceFactory.makeRetrofitService()
    override suspend fun getTrendingGifs(apiKey: String, context: Context): List<Gif> {
        if (!isInternetAvailable(context)) {
           //"no hay internet"
                val response= withContext(Dispatchers.IO) {
                    if (!localDataSource.isEmpty()) {
                        //todo: aca deberia comparar con la fecha actual no en general
                        localDataSource.getAll()
                    } else {
                        emptyList()
                    }
            }
            return response
        }else {
            var gifLocales = withContext(Dispatchers.IO) {
                localDataSource.getByDateTrending(obtenerFechaActual())
            }
            if(gifLocales.isEmpty()){
                val gifRemotos =withContext(Dispatchers.IO) {
                    val response= remoteApiDataSource.getTrendingGifs(apiKey)
                    localDataSource.save(response)
                    response
                }
                return gifRemotos.map { it.apiToDomain() }
            }else{
                return gifLocales
            }
        }
    }


    override suspend fun searchGifs(apiKey: String, search: String, context: Context): List<Gif> {
        return if(!isInternetAvailable(context)){
            localDataSource.getBySearch(search)
        }else{
            val gif =remoteApiDataSource.searchGifs(apiKey, search)
            localDataSource.save(gif)
            gif.map { it.apiToDomain() }
        }
    }
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
    fun obtenerFechaActual(): String {
        val calendario = Calendar.getInstance()
        val formato = SimpleDateFormat("yyyy-MM-dd")
        return formato.format(calendario.time)
    }

}