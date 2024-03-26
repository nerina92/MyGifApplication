package com.example.mygifapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.mygifapplication.data.ApiServiceFactory
import com.example.mygifapplication.data.model.ResponseTrendingGifs
import com.example.mygifapplication.ui.theme.MyGifApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val service = ApiServiceFactory.makeRetrofitService()

        /*lifecycleScope.launch {
          val gifs =   service.getTrendingGifs("H0RH6YWpWI2HWqTs6KuLil53ofyQM2hX")
            println(gifs)
        }*/

        setContent {
            // Utilizamos una variable de estado mutable para almacenar los GIFs
            var gifs by remember { mutableStateOf<ResponseTrendingGifs?>(null) }
            // Llamamos a la API y actualizamos la variable de estado una vez que se obtengan los datos
            LaunchedEffect(key1 = Unit) {
                gifs = service.getTrendingGifs("H0RH6YWpWI2HWqTs6KuLil53ofyQM2hX")
            }
            var searchText by remember { mutableStateOf("") }
            var title by remember { mutableStateOf("Trending Gifs") }

            MyGifApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    gifs?.let { g ->
                        MainScreen(
                            title = title,
                            trendingGifs = g,
                            searchText = searchText,
                            onSearchTextChanged = { searchText = it },
                            onSearchClicked = {
                                // Lógica para realizar la búsqueda
                                lifecycleScope.launch {
                                    if(searchText.isEmpty()){
                                        gifs = service.getTrendingGifs("H0RH6YWpWI2HWqTs6KuLil53ofyQM2hX")
                                        title = "Trending Gifs"
                                    }else{
                                        gifs = service.searchGifs("H0RH6YWpWI2HWqTs6KuLil53ofyQM2hX", searchText)
                                        title = "Resultados de búsqueda: $searchText"

                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    title : String,
    trendingGifs: ResponseTrendingGifs,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = onSearchTextChanged,
            onSearchClicked = onSearchClicked
        )
        Text(text = title)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            content={
            items(trendingGifs.data.take(25)){gif->
                MediaItem(gif.images.original.url, gif.title)
            }
        }
        )
    }

}
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = searchText,
            onValueChange = { onSearchTextChanged(it) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            placeholder = { Text(text = "Search") },
            singleLine = true
        )
        Button(
            onClick = { onSearchClicked() },
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(text = "Search")
        }
    }
}

@Composable
fun MediaItem(url: String, desc:String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .padding(20.dp)){
            AsyncImage(model = url , contentDescription = desc)
        }
    }
}


