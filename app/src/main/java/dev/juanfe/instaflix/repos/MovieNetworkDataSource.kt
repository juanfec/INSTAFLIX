package dev.juanfe.instaflix.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.remote.ApiCalls
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieNetworkDataSource (val apiService : ApiCalls, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState                   //with this get, no need to implement get function to get networkSate

    private val _downloadedMovieDetailsResponse =  MutableLiveData<Movie>()
    val downloadedMovieResponse: LiveData<Movie>
    get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                    apiService.getMovieDetails(movieId)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                            _downloadedMovieDetailsResponse.postValue(it)
                                            _networkState.postValue(NetworkState.LOADED)
                                    },
                                    {
                                            _networkState.postValue(NetworkState.ERROR)
                                            Log.e("MovieDetailsDataSource", it.message!!)
                                    }
                            )
            )

        }

        catch (e: Exception){
            Log.e("MovieDetailsDataSource", e.message!!)
        }


    }
}