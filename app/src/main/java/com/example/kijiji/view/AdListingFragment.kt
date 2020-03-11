package com.example.kijiji.view

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kijiji.R
import com.example.kijiji.adpater.AdListingAdapter
import com.example.kijiji.databinding.AdListingFragmentBinding
import com.example.kijiji.model.AdList
import com.example.kijiji.model.Ads
import com.example.kijiji.network.ApiEmptyResponse
import com.example.kijiji.network.ApiErrorResponse
import com.example.kijiji.network.ApiSuccessResponse
import com.example.kijiji.viewmodel.AdListingViewModel


class AdListingFragment: Fragment(), AdListingAdapter.AdItemClickListener {

    companion object {
        val TAG = AdListingFragment::class.java.canonicalName
        const val KEY_AD_ID = "adId"
    }

    private lateinit var adList: List<Ads>
    private var isLastPage = false
    private var isLoading = false
    private var nextPageUrl: String? = ""
    private var bundleData: AdList? = null
    private lateinit var adListingViewModel: AdListingViewModel
    private lateinit var adRecyclerView: RecyclerView
    private lateinit var adListAdapter: AdListingAdapter
    private lateinit var adLinearLayoutManager: LinearLayoutManager
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundleData = arguments?.getParcelable(SplashScreenFragment.AD_REPONSE_KEY)
        adList = bundleData!!.ads
        nextPageUrl = bundleData?.next_page_url
        adListAdapter = AdListingAdapter()
        adListAdapter.setAdClickLsitener(this)
        adListAdapter.setAdList(adList)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val adListingFragmentBinding = DataBindingUtil.inflate<AdListingFragmentBinding>(
            inflater, R.layout.ad_listing_fragment, container, false)
        val view = adListingFragmentBinding.root
        adRecyclerView = view.findViewById(R.id.ad_list_recycler_view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        adLinearLayoutManager = LinearLayoutManager(activity)
        adLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        adRecyclerView.layoutManager = adLinearLayoutManager
        adRecyclerView.addOnScrollListener(recyclerViewOnScrollListener)
        adRecyclerView.adapter = adListAdapter
        adListingViewModel = ViewModelProvider(this).get(AdListingViewModel::class.java)
    }


    override fun onAddItemClicked(ads: Ads?) {
        val bundle = bundleOf(KEY_AD_ID to ads?.id.toString())
        navController.navigate(R.id.action_adListingFragment_to_addDetailFragment, bundle)
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = adLinearLayoutManager.childCount
                val totalItemCount: Int = adLinearLayoutManager.itemCount
                val firstVisibleItemPosition: Int = adLinearLayoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
                    ) {
                        loadMoreItems()
                    }
                }
            }
        }

    private fun loadMoreItems() {
        isLoading = true
        Log.d(TAG, nextPageUrl)
        adListingViewModel.getNextAdResponse(nextPageUrl).observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiSuccessResponse -> {
                    adListAdapter.setAdList(it.body.ads)
                    if (it.body.next_page_url.isNullOrEmpty()) {
                        isLastPage = true
                        nextPageUrl = ""
                    } else {
                        nextPageUrl = it.body.next_page_url
                    }
                    isLoading = false
                }
                is ApiErrorResponse -> {
                    Log.d(TAG, it.errorMessage)
                    isLoading = false
                }
                is ApiEmptyResponse -> {
                    Log.d(TAG, it.toString())
                    isLoading = false
                }
            }
        })
    }
}