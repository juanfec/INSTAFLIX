package dev.juanfe.instaflix.ui.home.series

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.juanfe.instaflix.R
import dev.juanfe.instaflix.data.models.MovieGeneral
import dev.juanfe.instaflix.data.models.SerieGeneral
import dev.juanfe.instaflix.data.remote.IMAGE_URL
import dev.juanfe.instaflix.repos.NetworkState
import dev.juanfe.instaflix.ui.home.movies.PopularMoviePagedListAdapter
import dev.juanfe.instaflix.ui.movie.MovieActivity
import dev.juanfe.instaflix.ui.serie.SerieActivity
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*


class SeriePagedListAdapter(val context: Context) :PagedListAdapter<SerieGeneral, RecyclerView.ViewHolder>(
    SerieDiffCallback()
) {
    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return SerieItemViewHolder(
                view
            )
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return PopularMoviePagedListAdapter.NetworkStateItemViewHolder(
                view
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as SerieItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as PopularMoviePagedListAdapter.NetworkStateItemViewHolder).bind(networkState)
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




    class SerieDiffCallback : DiffUtil.ItemCallback<SerieGeneral>() {
        override fun areItemsTheSame(oldItem: SerieGeneral, newItem: SerieGeneral): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SerieGeneral, newItem: SerieGeneral): Boolean {
            return oldItem == newItem
        }

    }


    class SerieItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(serieGeneral: SerieGeneral?, context: Context) {


            val moviePosterURL = IMAGE_URL + serieGeneral?.poster_path
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.cv_iv_movie_poster);

            itemView.setOnClickListener{
                val intent = Intent(context, SerieActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("id", serieGeneral?.id)
                context.startActivity(intent)
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