package dev.juanfe.instaflix.reps

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.remote.ApiCalls
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : ApiCalls, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}