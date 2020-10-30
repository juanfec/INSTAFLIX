package dev.juanfe.instaflix.di

import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.data.remote.NetworkConnectionInterceptor
import org.koin.dsl.module

val appModule = module {

    single {NetworkConnectionInterceptor(get())}
    single {ApiCalls(get())}
}