package com.example.tournavigation

import org.koin.dsl.module

val appModel = module {
    single { MapState(get()) }
}
