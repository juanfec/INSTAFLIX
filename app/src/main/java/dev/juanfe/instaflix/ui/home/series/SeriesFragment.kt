package dev.juanfe.instaflix.ui.home.series

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dev.juanfe.instaflix.R
import dev.juanfe.instaflix.repos.NetworkState
import dev.juanfe.instaflix.util.waitForTransition
import kotlinx.android.synthetic.main.fragment_series.*
import org.koin.android.viewmodel.ext.android.viewModel

class SeriesFragment : Fragment() {

    val myViewModel: SeriesViewModel by viewModel()
    lateinit var  serieAdapter: SeriePagedListAdapter
    lateinit var gridLayoutManager: GridLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        serieAdapter =
            SeriePagedListAdapter(
                requireActivity().applicationContext
            )
        if (requireActivity().resources
                .configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        ) {
            gridLayoutManager = GridLayoutManager(context, 3)
        } else {
            gridLayoutManager = GridLayoutManager(context, 5)
        }
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = serieAdapter.getItemViewType(position)
                if (viewType == serieAdapter.MOVIE_VIEW_TYPE) return  1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };



        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_serie_list.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            adapter = serieAdapter
        }
        waitForTransition(rv_serie_list)
        myViewModel.serieGeneralPagedList.observe(requireActivity(), Observer {
            serieAdapter.submitList(it)
        })

        myViewModel.networkState.observe(requireActivity(), Observer {
            progress_bar_popular_series.visibility = if (myViewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular_series.visibility = if (myViewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!myViewModel.listIsEmpty()) {
                serieAdapter.setNetworkState(it)
            }
        })
    }




}