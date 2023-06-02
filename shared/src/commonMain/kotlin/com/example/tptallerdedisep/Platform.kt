package com.example.tptallerdedisep

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform