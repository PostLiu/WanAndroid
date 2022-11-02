package com.postliu.wanandroid.model.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.zj.banner.model.BaseBannerBean

@Keep
data class BannerEntity(
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("imagePath")
    val imagePath: Any,
    @SerializedName("isVisible")
    val isVisible: Int = 1,
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: Int = 1,
    @SerializedName("url")
    val url: String = ""
) : BaseBannerBean() {
    override val data: Any
        get() = imagePath
}