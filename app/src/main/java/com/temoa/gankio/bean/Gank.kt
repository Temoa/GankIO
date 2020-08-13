package com.temoa.gankio.bean

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

/**
 * Created by Temoa
 * on 2016/10/20 17:19
 */
data class Gank(
    @SerializedName("data") val gankData: List<GankData>,
    val page: Int,
    @SerializedName("page_count") val pageCount: Int,
    val status: Int,
    @SerializedName("total_counts") val totalCounts: Int
)

data class GankData(
    @SerializedName("_id") val id: String,
    val author: String,
    val category: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val likeCounts: Int,
    val publishedAt: String,
    val stars: Int,
    val title: String,
    val type: String,
    val url: String,
    val views: Int,

    @Ignore val _index: Long
)