package dev.juanfe.instaflix.di

import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.data.remote.NetworkConnectionInterceptor
import dev.juanfe.instaflix.ui.home.movies.MainActivityViewModel
import dev.juanfe.instaflix.ui.home.movies.MoviePagedListRepository
import dev.juanfe.instaflix.ui.home.series.SeriePagedListRepository
import dev.juanfe.instaflix.ui.home.series.SeriesViewModel
import dev.juanfe.instaflix.ui.movie.MovieActivityViewModel
import dev.juanfe.instaflix.ui.movie.MovieRepository
import dev.juanfe.instaflix.ui.serie.SerieActivityViewModel
import dev.juanfe.instaflix.ui.serie.SerieRepository
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val appModule = module {
    single {NetworkConnectionInterceptor(get())}
    single {ApiCalls(get())}
    single { MoviePagedListRepository(get()) }
    single { MovieRepository(get()) }
    single { SerieRepository(get()) }
    single { SeriePagedListRepository(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { MovieActivityViewModel(get()) }
    viewModel { SeriesViewModel(get()) }
    viewModel { SerieActivityViewModel(get()) }
}