package com.temoa.gankio.data.mapper

import com.temoa.gankio.bean.GankData
import com.temoa.gankio.data.local.FavEntity

/**
 * Created by lai
 * on 2020/8/13.
 */
class GankData2FavEntityMapper : Mapper<GankData, FavEntity> {

  override fun map(input: GankData): FavEntity {
    return FavEntity(
        input.id,
        input.title,
        input.author,
        input.type,
        input.url
    ).apply { index = input._index }
  }
}
