package dev.juanfe.instaflix.ui.serie

import androidx.lifecycle.LiveData
import dev.juanfe.instaflix.data.models.Serie
import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.repos.NetworkState
import dev.juanfe.instaflix.repos.SerieNetworkDataSource
import io.reactivex.disposables.CompositeDisposable

class SerieRepository (private val apiService : ApiCalls) {

    lateinit var serieDetailsNetworkDataSource: SerieNetworkDataSource

    fun fetchSingleSerieDetails (compositeDisposable: CompositeDisposable, serieId: Int) : LiveData<Serie> {

        serieDetailsNetworkDataSource = SerieNetworkDataSource(apiService,compositeDisposable)
        serieDetailsNetworkDataSource.fetchSerieDetails(serieId)

        return serieDetailsNetworkDataSource.downloadedSerieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return serieDetailsNetworkDataSource.networkState
    }



}