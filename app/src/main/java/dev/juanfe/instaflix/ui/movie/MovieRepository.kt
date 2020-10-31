package dev.juanfe.instaflix.ui.movie

import androidx.lifecycle.LiveData
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.repos.MovieNetworkDataSource
import dev.juanfe.instaflix.repos.NetworkState
import io.reactivex.disposables.CompositeDisposable


class MovieRepository (private val apiService : ApiCalls) {

    lateinit var movieDetailsNetworkDataSource: MovieNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<Movie> {

        movieDetailsNetworkDataSource = MovieNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }



}