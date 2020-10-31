package dev.juanfe.instaflix.repos

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import dev.juanfe.instaflix.data.models.SerieGeneral
import dev.juanfe.instaflix.data.remote.ApiCalls
import io.reactivex.disposables.CompositeDisposable

class SerieDataSourceFactory (private val apiService : ApiCalls, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, SerieGeneral>() {

    val moviesLiveDataSource =  MutableLiveData<SerieDataSource>()

    override fun create(): DataSource<Int, SerieGeneral> {
        val serieDataSource = SerieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(serieDataSource)
        return serieDataSource
    }
}