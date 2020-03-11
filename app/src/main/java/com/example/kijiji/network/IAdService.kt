package com.example.kijiji.network

import androidx.lifecycle.LiveData
import com.example.kijiji.model.AdDetail
import com.example.kijiji.model.AdList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface IAdService {

    @GET("ads")
    fun getAdListing(): LiveData<ApiResponse<AdList>>

    @GET("ad_detail/{id}")
    fun getAdDetails(@Path("id") id: String?): LiveData<ApiResponse<AdDetail>>

    @GET
    fun getNextAdListing(@Url url: String?): LiveData<ApiResponse<AdList>>
}