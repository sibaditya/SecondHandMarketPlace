package com.example.kijiji.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kijiji.model.AdList
import com.example.kijiji.network.ApiResponse
import com.example.kijiji.network.IAdService
import com.example.kijiji.network.RetrofitClient
import com.example.kijiji.util.UrlUtils

class AdListingViewModel: ViewModel() {
    fun getNextAdResponse(url: String?): LiveData<ApiResponse<AdList>> {
        return RetrofitClient().getRetrofit(UrlUtils.BASE_URL).create(IAdService::class.java).getNextAdListing(url)
    }
}