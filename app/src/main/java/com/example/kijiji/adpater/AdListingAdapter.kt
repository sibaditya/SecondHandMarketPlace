package com.example.kijiji.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kijiji.databinding.AdItemLayoutBinding
import com.example.kijiji.model.Ads

class AdListingAdapter(): RecyclerView.Adapter<AdListingAdapter.AdListingViewHolder>() {

    private val adList = ArrayList<Ads>()
    lateinit var adItemClickListener: AdItemClickListener

    interface AdItemClickListener {
        fun onAddItemClicked(ads: Ads?)
    }

    fun setAdClickLsitener(listener: AdItemClickListener) {
        adItemClickListener = listener
    }

    fun setAdList(list: List<Ads>) {
        adList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdListingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val adItemLayoutBinding: AdItemLayoutBinding = AdItemLayoutBinding.inflate(layoutInflater, parent, false)
        return AdListingViewHolder(adItemLayoutBinding)
    }

    override fun getItemCount(): Int{
        return adList?.size ?: 0
    }

    override fun onBindViewHolder(holder: AdListingViewHolder, position: Int) {
        val ad = adList?.get(position)
        holder.bind(ad, adItemClickListener)
    }

    class AdListingViewHolder(val binding: AdItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ads?, adItemClickListener: AdItemClickListener) {
            binding.ads = item
            itemView.setOnClickListener(View.OnClickListener {
                adItemClickListener.onAddItemClicked(item)
            })
            binding.executePendingBindings()
        }
    }
}