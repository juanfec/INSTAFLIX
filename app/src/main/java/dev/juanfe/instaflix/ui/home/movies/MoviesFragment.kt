package dev.juanfe.instaflix.ui.home.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dev.juanfe.instaflix.R
import dev.juanfe.instaflix.repos.NetworkState
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.android.viewmodel.ext.android.viewModel


class MoviesFragment : Fragment() {
    // Lazy Inject ViewModel
    val myViewModel: MainActivityViewModel by viewModel()
    lateinit var  movieAdapter: PopularMoviePagedListAdapter
    lateinit var gridLayoutManager: GridLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        movieAdapter =
            PopularMoviePagedListAdapter(
                requireActivity().applicationContext
            )
        gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        myViewModel.movieGeneralPagedList.observe(requireActivity(), Observer {
            movieAdapter.submitList(it)
        })

        myViewModel.networkState.observe(requireActivity(), Observer {
            progress_bar_popular.visibility = if (myViewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (myViewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!myViewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }


}