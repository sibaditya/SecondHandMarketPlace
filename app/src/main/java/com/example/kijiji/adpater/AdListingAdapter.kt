package com.example.kijiji.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.kijiji.R
import com.example.kijiji.databinding.AdItemLayoutBinding
import com.example.kijiji.model.Ads
import com.example.kijiji.util.Utils


class AdListingAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private val adList = ArrayList<Ads?>()
    private lateinit var adItemClickListener: AdItemClickListener
    private lateinit var viewHolder :RecyclerView.ViewHolder

    interface AdItemClickListener {
        fun onAddItemClicked(ads: Ads?)
    }

    fun setAdClickListener(listener: AdItemClickListener) {
        adItemClickListener = listener
    }

    fun setLoadingLayout() {
        if (!adList.isNullOrEmpty() && adList[adList.size - 1] != null) {
            adList.add(null)
            notifyItemInserted(adList.size - 1)
        }
    }

    fun removeLoadingView() {
        if (!adList.isNullOrEmpty() && adList[adList.size - 1] == null) {
            adList.removeAt(adList.size-1)
            notifyDataSetChanged()
        }
    }

    fun setAdList(list: List<Ads>) {
        if(!adList.isNullOrEmpty()) {
            adList.removeAt(adList.size-1)
        }
        adList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val adItemLayoutBinding: AdItemLayoutBinding = AdItemLayoutBinding.inflate(layoutInflater, parent, false)
            viewHolder = AdListingViewHolder(adItemLayoutBinding)
        } else if (viewType == VIEW_TYPE_LOADING) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.ad_list_item_loading, parent, false)
            viewHolder = LoadingViewHolder(view)
        }
        return viewHolder
    }

    override fun getItemCount(): Int{
        return adList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (adList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is LoadingViewHolder -> holder.progressBar
            is AdListingViewHolder -> holder.bind(adList[position], adItemClickListener)
        }
    }

    private class LoadingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val progressBar: LottieAnimationView = itemView.findViewById(R.id.ad_list_loading)
    }

    class AdListingViewHolder(val binding: AdItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ads?, adItemClickListener: AdItemClickListener) {
            binding.ads = item
            binding.date = Utils().getFormatedDate(item?.post_date)
            itemView.setOnClickListener {
                adItemClickListener.onAddItemClicked(item)
            }
            binding.executePendingBindings()
        }
    }


}