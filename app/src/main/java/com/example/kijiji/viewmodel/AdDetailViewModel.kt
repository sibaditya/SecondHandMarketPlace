package com.example.kijiji.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kijiji.model.AdDetail
import com.example.kijiji.model.AdList
import com.example.kijiji.network.ApiResponse
import com.example.kijiji.network.IAdService
import com.example.kijiji.network.RetrofitClient
import com.example.kijiji.util.UrlUtils

class AdDetailViewModel : ViewModel(){

    fun getAddDeatil(adId: String?): LiveData<ApiResponse<AdDetail>> {
        return RetrofitClient().getRetrofit(UrlUtils.BASE_URL).create(IAdService::class.java).getAdDetails(adId)
    }
}