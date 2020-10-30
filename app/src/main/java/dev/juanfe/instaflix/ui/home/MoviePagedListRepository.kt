package dev.juanfe.instaflix.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.data.remote.POST_PER_PAGE
import dev.juanfe.instaflix.reps.MovieDataSource
import dev.juanfe.instaflix.reps.MovieDataSourceFactory
import dev.juanfe.instaflix.reps.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiService : ApiCalls) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}