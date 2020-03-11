package com.example.kijiji.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price (
	@field:Json(name = "currency") val currency : String,
	@field:Json(name = "amount") val amount : Double
): Parcelable