package dev.juanfe.instaflix.ui.home.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import dev.juanfe.instaflix.data.models.SerieGeneral
import dev.juanfe.instaflix.repos.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SeriesViewModel(private val serieRepository : SeriePagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  serieGeneralPagedList : LiveData<PagedList<SerieGeneral>> by lazy {
        serieRepository.fetchLiveSeriePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        serieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return serieGeneralPagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}