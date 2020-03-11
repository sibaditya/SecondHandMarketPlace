package com.example.kijiji.model

import com.squareup.moshi.Json

data class AdDetail (
	@field:Json(name = "id") val id : Int,
	@field:Json(name = "user_id") val user_id : Int,
	@field:Json(name = "title") val title : String,
	@field:Json(name = "category_id") val category_id : Int,
	@field:Json(name = "post_date") val post_date : String,
	@field:Json(name = "location") val location : Location,
	@field:Json(name = "price") val price : Price,
	@field:Json(name = "thumbnail_url") val thumbnail_url : String,
	@field:Json(name = "description") val description : String,
	@field:Json(name = "email") val email : String,
	@field:Json(name = "image_urls") val image_urls : List<String>
)