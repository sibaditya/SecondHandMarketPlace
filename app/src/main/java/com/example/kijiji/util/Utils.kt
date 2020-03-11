package com.example.kijiji.util

import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.kijiji.R

class Utils {

    companion object {
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
    }

    fun isTextNullOrEmpty(inputText : CharSequence?) : Boolean {
        return inputText.isNullOrEmpty()
    }
}