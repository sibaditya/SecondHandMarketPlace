package com.example.kijiji.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.kijiji.R
import com.example.kijiji.util.Utils


class AdPagerAdapter: PagerAdapter() {

    private lateinit var adImageList: List<String>

    fun setAdImageList(list: List<String>) {
        adImageList = list
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =
            LayoutInflater.from(container.context).inflate(R.layout.ad_item, null)
        val imageView = view.findViewById<ImageView>(R.id.image)
        Utils.setImageResource(imageView, adImageList[position])
        container.addView(view)
        return view
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` == view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return adImageList.size
    }

}