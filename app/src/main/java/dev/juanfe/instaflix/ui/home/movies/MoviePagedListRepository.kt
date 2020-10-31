package dev.juanfe.instaflix.ui.home.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.juanfe.instaflix.data.models.MovieGeneral
import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.data.remote.POST_PER_PAGE
import dev.juanfe.instaflix.repos.MovieDataSource
import dev.juanfe.instaflix.repos.MovieDataSourceFactory
import dev.juanfe.instaflix.repos.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiService : ApiCalls) {

    lateinit var movieGeneralPagedList: LiveData<PagedList<MovieGeneral>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<MovieGeneral>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        movieGeneralPagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return movieGeneralPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}