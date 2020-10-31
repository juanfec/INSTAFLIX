package dev.juanfe.instaflix.ui.home.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.juanfe.instaflix.data.models.MovieGeneral
import dev.juanfe.instaflix.data.models.SerieGeneral
import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.data.remote.POST_PER_PAGE
import dev.juanfe.instaflix.repos.*
import io.reactivex.disposables.CompositeDisposable

class SeriePagedListRepository (private val apiService : ApiCalls) {

    lateinit var serieGeneralPagedList: LiveData<PagedList<SerieGeneral>>
    lateinit var serieDataSourceFactory: SerieDataSourceFactory

    fun fetchLiveSeriePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<SerieGeneral>> {
        serieDataSourceFactory = SerieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        serieGeneralPagedList = LivePagedListBuilder(serieDataSourceFactory, config).build()

        return serieGeneralPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<SerieDataSource, NetworkState>(
            serieDataSourceFactory.moviesLiveDataSource, SerieDataSource::networkState)
    }
}