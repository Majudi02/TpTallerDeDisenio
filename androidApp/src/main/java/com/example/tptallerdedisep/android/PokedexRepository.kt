package com.example.tptallerdedisep.android

import retrofit2.Response

interface PokedexRepository {
    suspend fun getPokedex() : Response<Pokedex>
}
