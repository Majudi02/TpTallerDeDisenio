package com.example.tptallerdedisep.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    private lateinit var viewModel: PokedexViewModel
    private val pokemonList = mutableListOf<PokedexResults>()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val isLoading = remember { mutableStateOf(false) }

                    // Listen to Retrofit response
                    viewModel = ViewModelProvider(
                        this,
                        PokedexViewModelFactory()
                    )[PokedexViewModel::class.java]


                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.screenState.collect {
                                when (it) {
                                    PokedexScreenState.Loading -> showLoading(isLoading)
                                    PokedexScreenState.Error -> handlerError()
                                    is PokedexScreenState.ShowPokedex -> showPokedex(
                                        it.pokedex,
                                        isLoading
                                    )
                                }
                            }
                        }
                    }

                    main(pokemonList)


                }
            }
        }
    }

    private fun showPokedex(pokedex: Pokedex, loading: MutableState<Boolean>) {
        loading.value = false
        updatePokedex(pokedex.results)
    }

    private fun handlerError() {
        Toast.makeText(this, "Error buscando la informacion", Toast.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: MutableState<Boolean>) {
        isLoading.value = true
    }


    @Composable
    fun progressBar(isLoading: MutableState<Boolean>) {
        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Aquí se muestra el ProgressBar
                    if (isLoading.value) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    @Composable
    fun main(pokemonList: MutableList<PokedexResults>) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            for (i in 0..12) {
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            //    model ="https://w7.pngwing.com/pngs/179/816/png-transparent-poke-ball-thumbnail.png",
                            model = pokemonList[1].url,
                            contentDescription = "fOTO",
                            modifier = Modifier.size(130.dp, 130.dp)
                        )
                        AsyncImage(
                            model = "https://w7.pngwing.com/pngs/179/816/png-transparent-poke-ball-thumbnail.png",
                            contentDescription = "fOTO",
                            modifier = Modifier.size(130.dp, 130.dp)
                        )
                        AsyncImage(
                            model = "https://w7.pngwing.com/pngs/179/816/png-transparent-poke-ball-thumbnail.png",
                            contentDescription = "fOTO",
                            modifier = Modifier.size(130.dp, 130.dp)
                        )
                    }

                }
            }
        }
    }

    fun updatePokedex(results: List<PokedexResults>?) {
        pokemonList.clear()
        if (results != null) {
            pokemonList.addAll(results)
        }
    }
}




