package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @field:SerializedName("articles")
    val articles: List<ArticlesItem>?

)

data class ArticlesItem(

    @field:SerializedName("publishedAt")
    val publishedAt: String,

    @field:SerializedName("author")
    val author: String?,

    @field:SerializedName("urlToImage")
    val urlToImage: String?,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("url")
    val url: String,

)