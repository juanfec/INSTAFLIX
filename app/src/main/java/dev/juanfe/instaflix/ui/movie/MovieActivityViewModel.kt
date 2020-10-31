package dev.juanfe.instaflix.ui.movie


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.repos.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieActivityViewModel (private val movieRepository : MovieRepository)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var movieId = 0

    val  movieDetails : LiveData<Movie> by lazy {
         movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    fun setMovieId( movieIdParam: Int){
        movieId = movieIdParam
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}