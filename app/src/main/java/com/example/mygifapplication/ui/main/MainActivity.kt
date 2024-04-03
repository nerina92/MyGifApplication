package com.example.mygifapplication.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mygifapplication.ui.theme.MyGifApplicationTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.mygifapplication.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   // private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val service = ApiServiceFactory.makeRetrofitService()
        /*lifecycleScope.launch {
                  val gifs =   service.getTrendingGifs("H0RH6YWpWI2HWqTs6KuLil53ofyQM2hX")
                    println(gifs)
        }*/


        setContent {


            //var searchText by remember { mutableStateOf("") }
            //var title by remember { mutableStateOf("Trending Gifs") }

            MyGifApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        MainScreen()

                }
            }
        }
    }
}

@Composable
fun MainScreen(
    //Si quisiera inyectar un ViewModel directamente a compose, podriamos hacerlo de la siguiente manera:
    viewModel: MainViewModel = hiltViewModel()
) {
    // Utilizamos una variable de estado mutable para almacenar los GIFs
    val gifs by viewModel.gifs.observeAsState()
    val title by viewModel.title.observeAsState()
    val searchText by viewModel.searchText.observeAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar(
            searchText = searchText?: "",
            onSearchTextChanged = {viewModel.setSearchText(it)},//onSearchTextChanged,
            onSearchClicked = { viewModel.getGiftSearch() }
        )
        Text(text = title?:"Trending Gifs")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            if (gifs?.isNotEmpty() == true) {
                items(gifs!!.take(25)) { gif ->
                    MediaItem(gif.url, gif.title)
                }
            }
        }
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

/*
@Composable
fun MediaItem(url: String, desc:String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .padding(20.dp)){
            AsyncImage(model = url , contentDescription = desc)
        }
    }
}
*/


@Composable
fun MediaItem(url: String, desc: String) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = desc
    ) {
        val state = painter.state
        println("url media item:  $url")
        println("State en media item:  $state")
        if (state is AsyncImagePainter.State.Loading){
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if ( state is AsyncImagePainter.State.Error) {
                Column {
                    val painter: Painter = painterResource(id = R.drawable.error_image)
                    Image(painter = painter, contentDescription = null)
                    Text(text = desc)
                }
                
            }else{
                SubcomposeAsyncImageContent()
            }
        }
    }
}