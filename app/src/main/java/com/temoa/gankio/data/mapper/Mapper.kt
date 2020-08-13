package com.temoa.gankio.data.mapper

/**
 * Created by lai
 * on 2020/8/13.
 */
interface Mapper<I, O> {
  fun map(input: I): O
}