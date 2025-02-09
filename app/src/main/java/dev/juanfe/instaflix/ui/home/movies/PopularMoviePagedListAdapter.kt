package dev.juanfe.instaflix.ui.home.movies

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.juanfe.instaflix.R
import dev.juanfe.instaflix.data.models.MovieGeneral
import dev.juanfe.instaflix.data.remote.IMAGE_URL
import dev.juanfe.instaflix.repos.NetworkState
import dev.juanfe.instaflix.ui.movie.MovieFragment
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*


class PopularMoviePagedListAdapter( val context: Context) : PagedListAdapter<MovieGeneral, RecyclerView.ViewHolder>(
    MovieDiffCallback()
) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return MovieItemViewHolder(
                view
            )
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(
                view
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }




    class MovieDiffCallback : DiffUtil.ItemCallback<MovieGeneral>() {
        override fun areItemsTheSame(oldItem: MovieGeneral, newItem: MovieGeneral): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieGeneral, newItem: MovieGeneral): Boolean {
            return oldItem == newItem
        }

    }


    class MovieItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movieGeneral: MovieGeneral?, context: Context) {
            var videoId = movieGeneral?.id!!
            val moviePosterURL = IMAGE_URL + movieGeneral?.poster_path
            itemView.cv_iv_movie_poster.apply {
                transitionName = videoId.toString()
                Glide.with(itemView.context)
                    .load(moviePosterURL)
                    .into(this);
            }

            itemView.setOnClickListener{
                val directions = MoviesFragmentDirections.actionNavigationMoviesToMovieFragment(videoId)
                val extras = FragmentNavigatorExtras(
                        itemView.cv_iv_movie_poster to videoId.toString())
                itemView.findNavController().navigate(directions,extras)
                /*
                val intent = Intent(context, MovieFragment::class.java)
                itemView.cv_iv_movie_poster.setTransitionName("posterMovie")
                val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(context as (Activity), itemView.cv_iv_movie_poster,itemView.cv_iv_movie_poster.transitionName)
                Log.e("Animation", "Success")
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("id", movieGeneral?.id)
                context.startActivity(intent,options.toBundle())

                 */
            }

        }

    }

    open class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE;
            }
            else  {
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }




}