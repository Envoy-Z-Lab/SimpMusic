package com.envoyz.riff.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.envoyz.riff.data.repository.MainRepository
import com.envoyz.riff.pagination.RecentPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentlySongsViewModel @Inject constructor(application: Application, private val mainRepository: MainRepository): AndroidViewModel(application) {
        val recentlySongs = Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
        ) {
            RecentPagingSource(mainRepository)
        }.flow.cachedIn(viewModelScope)
}