package com.example.kijiji.view

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
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
import com.example.kijiji.util.Utils.Companion.EMPTY_STRING_BUNDLE
import com.example.kijiji.viewmodel.AdListingViewModel


class AdListingFragment: AdParentFragment(), AdListingAdapter.AdItemClickListener {

    companion object {
        val TAG = AdListingFragment::class.java.canonicalName
        const val KEY_AD_ID = "adId"
    }

    private var isLastPage = false
    private var isLoading = false
    private var nextPageUrl: String? = ""
    private var adListBundle: AdList? = null
    private var adErrorMessage: String? = ""

    private lateinit var adList: List<Ads>
    private lateinit var adListingViewModel: AdListingViewModel
    private lateinit var adRecyclerView: RecyclerView
    private lateinit var adErrorLayout: LinearLayout
    private lateinit var adErrorMessageTextView: TextView
    private lateinit var adListAdapter: AdListingAdapter
    private lateinit var adLinearLayoutManager: LinearLayoutManager
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adListBundle = arguments?.getParcelable(SplashScreenFragment.AD_REPONSE_KEY)
        adListBundle?.let {
            adList = adListBundle!!.ads
            nextPageUrl = adListBundle?.next_page_url
            adListAdapter = AdListingAdapter()
            adListAdapter.setAdClickListener(this)
            adListAdapter.setAdList(adList)
        }
        adErrorMessage = arguments?.getString(SplashScreenFragment.AD_ERROR_RESPONSE)
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
        adErrorLayout = view.findViewById(R.id.error_layout)
        adErrorMessageTextView = view.findViewById(R.id.tv_error_message)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adErrorMessage.equals(EMPTY_STRING_BUNDLE)) {
            adRecyclerView.visibility = View.VISIBLE
            adErrorLayout.visibility = View.GONE
            navController = Navigation.findNavController(view)
            adLinearLayoutManager = LinearLayoutManager(activity)
            adLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            adRecyclerView.layoutManager = adLinearLayoutManager
            adRecyclerView.addOnScrollListener(recyclerViewOnScrollListener)
            adRecyclerView.adapter = adListAdapter
            adListingViewModel = ViewModelProvider(this).get(AdListingViewModel::class.java)
            adListingViewModel.getNextAdResponse(nextPageUrl)
        } else {
            handleErrorState()
        }

    }

    override fun onAddItemClicked(ads: Ads?) {
        val bundle = bundleOf(KEY_AD_ID to ads?.id.toString())
        navController.navigate(R.id.action_adListingFragment_to_addDetailFragment, bundle)
    }

    private fun handleErrorState() {
        adRecyclerView.visibility = View.GONE
        adErrorLayout.visibility = View.VISIBLE
        adErrorMessageTextView.text = getString(R.string.error_message, adErrorMessage)
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {

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
        adListAdapter.setLoadingLayout()
        isLoading = true
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
                    adListAdapter.removeLoadingView()
                    showToast(it.errorMessage)
                }
                is ApiEmptyResponse -> {
                    Log.d(TAG, it.toString())
                    isLoading = false
                    adListAdapter.removeLoadingView()
                    showToast(it.toString())
                }
            }
        })
    }
}