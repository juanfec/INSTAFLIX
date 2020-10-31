package dev.juanfe.instaflix.ui.serie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.models.Serie
import dev.juanfe.instaflix.repos.NetworkState
import dev.juanfe.instaflix.ui.movie.MovieRepository
import io.reactivex.disposables.CompositeDisposable

class SerieActivityViewModel (private val serieRepository : SerieRepository)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var serieId = 0

    val  serieDetails : LiveData<Serie> by lazy {
        serieRepository.fetchSingleSerieDetails(compositeDisposable, serieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        serieRepository.getMovieDetailsNetworkState()
    }

    fun setSerieId( movieIdParam: Int){
        serieId = movieIdParam
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}