package com.example.kijiji.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (

	@field:Json(name = "address") val address : String,
	@field:Json(name = "latitude") val latitude : Double,
	@field:Json(name = "longitude") val longitude : Double
): Parcelable