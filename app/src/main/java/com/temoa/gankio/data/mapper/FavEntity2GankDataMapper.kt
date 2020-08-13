package com.temoa.gankio.data.mapper

import com.temoa.gankio.bean.GankData
import com.temoa.gankio.data.local.FavEntity

/**
 * Created by lai
 * on 2020/8/13.
 */
class FavEntity2GankDataMapper : Mapper<FavEntity, GankData> {

  override fun map(input: FavEntity): GankData {
    return GankData(
        id = input.id,
        author = input.author,
        category = "",
        createdAt = "",
        desc = "",
        images = emptyList(),
        likeCounts = 0,
        publishedAt = "",
        stars = 0,
        title = input.title,
        type = input.type,
        url = input.url,
        views = 0,
        _index = input.index
    )
  }
}