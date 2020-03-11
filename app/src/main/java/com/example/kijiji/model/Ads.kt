package com.example.kijiji.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Ads (
	@field:Json(name = "id") val id : Int,
	@field:Json(name = "user_id") val user_id : Int,
	@field:Json(name = "title") val title : String,
	@field:Json(name = "category_id") val category_id : Int,
	@field:Json(name = "post_date") val post_date : String,
	@field:Json(name = "location") val location : Location,
	@field:Json(name = "price") val price : Price,
	@field:Json(name = "thumbnail_url") val thumbnail_url : String
): Parcelable