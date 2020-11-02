package dev.juanfe.instaflix.ui.serie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.setTransitionName
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import dev.juanfe.instaflix.R
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.models.Serie
import dev.juanfe.instaflix.data.remote.IMAGE_URL
import dev.juanfe.instaflix.repos.NetworkState
import dev.juanfe.instaflix.ui.movie.MovieActivityViewModel
import dev.juanfe.instaflix.ui.movie.MovieFragmentArgs
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.progress_bar
import kotlinx.android.synthetic.main.activity_movie.txt_error
import kotlinx.android.synthetic.main.activity_serie.*
import kotlinx.android.synthetic.main.fragment_series.*
import org.koin.android.viewmodel.ext.android.viewModel

class SerieFragment :  Fragment() {
    val viewModel: SerieActivityViewModel by viewModel()
    var serieId: Int? = null
    val args: SerieFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_serie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serieId = args.serieId
        setTransitionName(iv_serie_poster, serieId.toString())
        viewModel.setSerieId(serieId!!)

        viewModel.serieDetails.observe(requireActivity(), androidx.lifecycle.Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(requireActivity(), androidx.lifecycle.Observer {
            progress_bar_serie.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_serie.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    fun bindUI( it: Serie){
        serie_title.text = it.title
        serie_release_date.text = it.releaseDate
        serie_rating.text = it.rating.toString()
        serie_overview.text = it.overview
        val seriePosterURL = IMAGE_URL + it.posterPath
        iv_serie_poster.apply {
            transitionName = serieId.toString()
            Glide.with(this)
                .load(seriePosterURL)
                .into(this);


        }

    }
}