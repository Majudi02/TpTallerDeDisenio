package com.example.tptallerdedisep.android

sealed class PokedexScreenState {
    object Loading : PokedexScreenState()

    object Error : PokedexScreenState()

    class ShowPokedex(val pokedex : Pokedex) : PokedexScreenState()
}
