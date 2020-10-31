package dev.juanfe.instaflix.di

import androidx.lifecycle.SavedStateHandle
import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.data.remote.NetworkConnectionInterceptor
import dev.juanfe.instaflix.ui.home.MainActivityViewModel
import dev.juanfe.instaflix.ui.home.MoviePagedListRepository
import dev.juanfe.instaflix.ui.movie.MovieActivityViewModel
import dev.juanfe.instaflix.ui.movie.MovieRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val appModule = module {
    single {NetworkConnectionInterceptor(get())}
    single {ApiCalls(get())}
    single { MoviePagedListRepository(get()) }
    single { MovieRepository(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { MovieActivityViewModel(get()) }
}