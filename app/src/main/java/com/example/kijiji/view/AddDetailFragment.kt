package com.example.kijiji.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.kijiji.R
import com.example.kijiji.adpater.AdPagerAdapter
import com.example.kijiji.databinding.AdDetailFragmentBinding
import com.example.kijiji.network.ApiEmptyResponse
import com.example.kijiji.network.ApiErrorResponse
import com.example.kijiji.network.ApiSuccessResponse
import com.example.kijiji.view.AdListingFragment.Companion.KEY_AD_ID
import com.example.kijiji.viewmodel.AdDetailViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import me.relex.circleindicator.CircleIndicator

class AddDetailFragment : Fragment() {

    companion object {
        val TAG = AddDetailFragment.javaClass.canonicalName
    }

    private var adId :String? = ""

    private lateinit var viewPager: ViewPager
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var adDetailViewModel: AdDetailViewModel
    private lateinit var adListingFragmentBinding: AdDetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adId = arguments?.getString(KEY_AD_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        adListingFragmentBinding = DataBindingUtil.inflate<AdDetailFragmentBinding>(
            inflater, R.layout.ad_detail_fragment, container, false)
        val view = adListingFragmentBinding.root
        viewPager = view.findViewById(R.id.view_pager)
        circleIndicator = view.findViewById(R.id.circle_indicator)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adDetailViewModel = ViewModelProvider(this).get(AdDetailViewModel::class.java)
        getAdDetailData()
    }

    private fun getAdDetailData() {
        adDetailViewModel.getAddDeatil(adId).observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiSuccessResponse ->{
                    Log.d(TAG, it.body.toString())
                    adListingFragmentBinding.adDetail = it.body
                    val pageAdapter = AdPagerAdapter()
                    pageAdapter.setAdImageList(it.body.image_urls)
                    viewPager.adapter = pageAdapter
                    circleIndicator.setViewPager(viewPager)
                }

                is ApiErrorResponse -> {
                    Log.d(TAG, it.errorMessage)

                }
                is ApiEmptyResponse -> {
                    Log.d(TAG, it.toString())

                }

            }
        })
    }
}