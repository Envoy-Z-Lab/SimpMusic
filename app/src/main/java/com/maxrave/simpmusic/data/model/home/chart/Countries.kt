package com.envoyz.riff.data.model.home.chart


import com.google.gson.annotations.SerializedName

data class Countries(
    @SerializedName("options")
    val options: List<String>,
    @SerializedName("selected")
    val selected: Selected
)