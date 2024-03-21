package com.envoyz.riff.data.model.home

import com.envoyz.riff.data.model.explore.mood.Mood
import com.envoyz.riff.data.model.home.chart.Chart
import com.envoyz.riff.utils.Resource


data class HomeResponse(
    val homeItem: Resource<ArrayList<HomeItem>>,
    val exploreMood: Resource<Mood>,
    val exploreChart: Resource<Chart>
)