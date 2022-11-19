package com.postliu.wanandroid.dao.converters

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.postliu.wanandroid.utils.GsonUtils
import com.postliu.wanandroid.model.entity.ArticleEntity

/**
 * 文章Tag的转换
 *
 * @constructor Create empty Article tag converters
 */
class ArticleTagConverters {

    @TypeConverter
    fun list2json(tags: List<ArticleEntity.Tag>): String {
        return GsonUtils.toJson(tags)
    }

    @TypeConverter
    fun json2list(json: String): List<ArticleEntity.Tag> {
        return GsonUtils.fromJson(json, object : TypeToken<List<ArticleEntity.Tag>>() {}.type)
    }
}