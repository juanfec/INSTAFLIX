package dev.juanfe.instaflix.ui.serie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import dev.juanfe.instaflix.R
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.models.Serie
import dev.juanfe.instaflix.data.remote.IMAGE_URL
import dev.juanfe.instaflix.repos.NetworkState
import dev.juanfe.instaflix.ui.movie.MovieActivityViewModel
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.progress_bar
import kotlinx.android.synthetic.main.activity_movie.txt_error
import kotlinx.android.synthetic.main.activity_serie.*
import kotlinx.android.synthetic.main.fragment_series.*
import org.koin.android.viewmodel.ext.android.viewModel

class SerieActivity : AppCompatActivity() {
    val viewModel: SerieActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie)

        val serieId: Int = intent.getIntExtra("id",1)

        viewModel.setSerieId(serieId)

        viewModel.serieDetails.observe(this, androidx.lifecycle.Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, androidx.lifecycle.Observer {
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
        Glide.with(this)
            .load(seriePosterURL)
            .into(iv_serie_poster);


    }
}