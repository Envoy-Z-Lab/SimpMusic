package com.envoyz.riff.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.envoyz.riff.data.model.browse.album.Track

@Entity(tableName = "queue")
data class QueueEntity(
    @PrimaryKey(autoGenerate = false)
    val queueId: Long = 0,
    val listTrack: List<Track>
)