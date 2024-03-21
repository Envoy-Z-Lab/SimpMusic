package com.envoyz.riff.data.model.home

import com.envoyz.riff.data.model.explore.mood.Mood
import com.envoyz.riff.data.model.home.chart.Chart
import com.envoyz.riff.utils.Resource

data class HomeDataCombine(
    val home: Resource<ArrayList<HomeItem>>,
    val mood: Resource<Mood>,
    val chart: Resource<Chart>,
    val newRelease: Resource<ArrayList<HomeItem>>,
) {
}