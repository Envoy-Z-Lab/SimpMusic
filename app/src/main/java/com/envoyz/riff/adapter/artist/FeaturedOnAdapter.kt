package com.envoyz.riff.adapter.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.envoyz.riff.R
import com.envoyz.riff.data.model.browse.artist.ResultPlaylist
import com.envoyz.riff.databinding.ItemSinglesBinding

class FeaturedOnAdapter(private var playlistList: ArrayList<ResultPlaylist>) :
    RecyclerView.Adapter<FeaturedOnAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int, type: String = "playlist")
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    inner class ViewHolder(val binding: ItemSinglesBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }

    fun updateList(newList: ArrayList<ResultPlaylist>) {
        playlistList.clear()
        playlistList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSinglesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun getItemCount(): Int {
        return playlistList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = playlistList[position]
        with(holder.binding) {
            tvAlbumName.text = album.title
            tvAlbumYear.text = album.author
            ivAlbumArt.load(album.thumbnails.lastOrNull()?.url) {
                crossfade(true)
                placeholder(R.drawable.holder)
            }
        }
    }

    fun getItem(position: Int): ResultPlaylist {
        return playlistList[position]
    }
}