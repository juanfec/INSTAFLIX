package dev.juanfe.instaflix.repos

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import dev.juanfe.instaflix.data.models.MovieGeneral
import dev.juanfe.instaflix.data.remote.ApiCalls
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : ApiCalls, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, MovieGeneral>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, MovieGeneral> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}