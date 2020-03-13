package com.example.kijiji.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.kijiji.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {

        const val EMPTY_STRING_BUNDLE = "None"

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @JvmStatic
        @BindingAdapter("imageResource")
        fun setImageResource(imageView: ImageView, imageUrl: String) {
            var context = imageView.context
            Glide.with(context)
                .load(imageUrl)
                .placeholder(context.getDrawable(R.drawable.kijiji_logo))
                .into(imageView)
        }

        fun isInternetAvailable(context: Context): Boolean {
            val cm =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }

    fun isTextNullOrEmpty(inputText : CharSequence?) : Boolean {
        return inputText.isNullOrEmpty()
    }

    fun getFormatedDate(date: String?): String {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val output = SimpleDateFormat("dd-MMM-yyyy")

        var d: Date? = null
        try {
            d = input.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return output.format(d)
    }
}