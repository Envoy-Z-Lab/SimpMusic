package com.envoyz.riff.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.envoyz.riff.R
import com.envoyz.riff.common.Config
import com.envoyz.riff.data.db.entities.AlbumEntity
import com.envoyz.riff.data.db.entities.ArtistEntity
import com.envoyz.riff.data.db.entities.PlaylistEntity
import com.envoyz.riff.data.db.entities.SongEntity
import com.envoyz.riff.data.model.browse.album.Track
import com.envoyz.riff.data.queue.Queue
import com.envoyz.riff.databinding.FragmentRecentlySongsBinding
import com.envoyz.riff.extension.navigateSafe
import com.envoyz.riff.extension.toTrack
import com.envoyz.riff.pagination.RecentLoadStateAdapter
import com.envoyz.riff.pagination.RecentPagingAdapter
import com.envoyz.riff.viewModel.RecentlySongsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RecentlySongsFragment: Fragment() {
    private var _binding: FragmentRecentlySongsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RecentlySongsViewModel>()

    private lateinit var mainAdapter: RecentPagingAdapter
    private lateinit var loadAdapter: RecentLoadStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecentlySongsBinding.inflate(inflater, container, false)
        binding.topAppBarLayout.applyInsetter {
            type(statusBars = true) {
                margin()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainAdapter = RecentPagingAdapter(requireContext())
        loadAdapter = RecentLoadStateAdapter()

        binding.rvRecentlySongs.apply {
            adapter = mainAdapter.withLoadStateFooter(loadAdapter)
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                viewModel.recentlySongs.collectLatest { pagingData ->
                    mainAdapter.submitData(pagingData) }
            }
        }
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        mainAdapter.setOnClickListener(object : RecentPagingAdapter.onItemClickListener {
            override fun onItemClick(position: Int, type: String) {
                if (type == "artist"){
                    val channelId = (mainAdapter.getItemByIndex(position) as ArtistEntity).channelId
                    val args = Bundle()
                    args.putString("channelId", channelId)
                    findNavController().navigateSafe(R.id.action_global_artistFragment, args)
                }
                if (type == Config.ALBUM_CLICK){
                    val browseId = (mainAdapter.getItemByIndex(position) as AlbumEntity).browseId
                    val args = Bundle()
                    args.putString("browseId", browseId)
                    findNavController().navigateSafe(R.id.action_global_albumFragment, args)
                }
                if (type == Config.PLAYLIST_CLICK){
                    val id = (mainAdapter.getItemByIndex(position) as PlaylistEntity).id
                    val args = Bundle()
                    args.putString("id", id)
                    findNavController().navigateSafe(R.id.action_global_playlistFragment, args)
                }
                if (type == Config.SONG_CLICK){
                    val songClicked = mainAdapter.getItemByIndex(position) as SongEntity
                    val videoId = songClicked.videoId
                    Queue.clear()
                    val firstQueue: Track = songClicked.toTrack()
                    Queue.setNowPlaying(firstQueue)
                    val args = Bundle()
                    args.putString("videoId", videoId)
                    args.putString("from", getString(R.string.recently_added))
                    args.putString("type", Config.SONG_CLICK)
                    findNavController().navigateSafe(R.id.action_global_nowPlayingFragment, args)
                }
            }
        })
    }
}