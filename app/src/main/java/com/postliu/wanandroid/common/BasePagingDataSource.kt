package com.postliu.wanandroid.common

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

abstract class BasePagingSource<Value : Any> : PagingSource<Int, Value>() {

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        return loadPaging(params)
    }

    abstract suspend fun loadPaging(loadParams: LoadParams<Int>): LoadResult<Int, Value>

}

/**
 * 创建一个默认的[PagingConfig]
 */
val defaultPagingConfig = PagingConfig(pageSize = 10, initialLoadSize = 10, prefetchDistance = 1)

/**
 * 封装[PagingSource]，简化创建流程
 *
 * @param T 数据类型
 * @param block 具体的请求结果块
 */
inline fun <reified T : Any> intKeyPagingSource(
    crossinline block: suspend (page: Int) -> List<T>
) = object : BasePagingSource<T>() {

    override suspend fun loadPaging(loadParams: LoadParams<Int>): LoadResult<Int, T> {
        return kotlin.runCatching {
            val page = loadParams.key ?: 1
            val dataList = block.invoke(page)
            LoadResult.Page(
                data = dataList,
                prevKey = null,
                nextKey = if (dataList.isEmpty()) null else page + 1
            )
        }.getOrElse { LoadResult.Error(it) }
    }
}

/**
 * 封装[Pager],简化创建流程
 *
 * @param T 数据源类型
 * @param block 数据请求块
 */
inline fun <reified T : Any> launchPagingFlow(
    crossinline block: suspend (page: Int) -> List<T>
) = Pager(
    config = defaultPagingConfig,
    pagingSourceFactory = {
        intKeyPagingSource {
            block.invoke(it)
        }
    }
).flow.flowOn(Dispatchers.IO)

inline fun <reified T : Any> intKeyZeroPagingSource(
    crossinline block: suspend (page: Int) -> List<T>
) = object : BasePagingSource<T>() {

    override suspend fun loadPaging(loadParams: LoadParams<Int>): LoadResult<Int, T> {
        return kotlin.runCatching {
            val page = loadParams.key ?: 0
            val dataList = block.invoke(page)
            LoadResult.Page(
                data = dataList,
                prevKey = null,
                nextKey = if (dataList.isEmpty()) null else page + 1
            )
        }.getOrElse { LoadResult.Error(it) }
    }
}

inline fun <reified T : Any> launchPagingFromZeroFlow(
    crossinline block: suspend (page: Int) -> List<T>
) = Pager(
    config = defaultPagingConfig,
    pagingSourceFactory = {
        intKeyZeroPagingSource {
            block.invoke(it)
        }
    }
).flow.flowOn(Dispatchers.IO)