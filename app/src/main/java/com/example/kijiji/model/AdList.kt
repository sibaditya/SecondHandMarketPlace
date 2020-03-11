package com.example.kijiji.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdList (
	@field:Json(name = "ads") val ads : List<Ads>,
	@field:Json(name = "next_page_url") val next_page_url : String
): Parcelable