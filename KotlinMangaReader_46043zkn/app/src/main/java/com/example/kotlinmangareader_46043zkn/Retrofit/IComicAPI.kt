package com.example.kotlinmangareader_46043zkn.Retrofit

import com.example.kotlinmangareader_46043zkn.Model.Banner
import com.example.kotlinmangareader_46043zkn.Model.Comic
import io.reactivex.Observable
import retrofit2.http.GET

interface IComicAPI {
    @get:GET(value = "banner")
    val bannerList:Observable<List<Banner>>
    @get:GET(value = "comic")
    val comicList:Observable<List<Comic>>
}