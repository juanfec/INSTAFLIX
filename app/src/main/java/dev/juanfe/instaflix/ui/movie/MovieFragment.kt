package dev.juanfe.instaflix.ui.movie

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import dev.juanfe.instaflix.R
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.data.remote.IMAGE_URL
import dev.juanfe.instaflix.repos.NetworkState
import dev.juanfe.instaflix.util.waitForTransition
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*

class MovieFragment : Fragment()  {
    val viewModel: MovieActivityViewModel by viewModel()
    var movieId: Int? = null
    val args: MovieFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = args.movieId
        setTransitionName(iv_movie_poster, movieId.toString())
        viewModel.setMovieId(movieId!!)

        viewModel.movieDetails.observe(requireActivity(), androidx.lifecycle.Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(requireActivity(), androidx.lifecycle.Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    fun bindUI( it: Movie){
        movie_title.text = it.title
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_overview.text = it.overview
        val moviePosterURL = IMAGE_URL + it.posterPath
        iv_movie_poster.apply {
            transitionName = movieId.toString()
            Glide.with(this)
                .load(moviePosterURL)
                .into(this);
        }


    }
}