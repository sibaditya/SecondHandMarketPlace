package com.example.kijiji.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.example.kijiji.R
import com.example.kijiji.adpater.AdPagerAdapter
import com.example.kijiji.databinding.AdDetailFragmentBinding
import com.example.kijiji.model.AdDetail
import com.example.kijiji.network.ApiEmptyResponse
import com.example.kijiji.network.ApiErrorResponse
import com.example.kijiji.network.ApiSuccessResponse
import com.example.kijiji.view.AdListingFragment.Companion.KEY_AD_ID
import com.example.kijiji.viewmodel.AdDetailViewModel
import me.relex.circleindicator.CircleIndicator
import java.util.*


class AddDetailFragment : AdParentFragment() {

    companion object {
        val TAG = AddDetailFragment::class.java.canonicalName
    }

    private var adId :String? = ""

    private lateinit var viewPager: ViewPager
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var adDetailViewModel: AdDetailViewModel
    private lateinit var adListingFragmentBinding: AdDetailFragmentBinding
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var adDetailLayout: ScrollView
    private lateinit var adDetailLoadingLayout: LinearLayout
    private lateinit var adErrorLayout: LinearLayout
    private lateinit var adErrorMessageTextView: TextView

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
        adListingFragmentBinding.adDetailFragment = this
        viewPager = view.findViewById(R.id.view_pager)
        circleIndicator = view.findViewById(R.id.circle_indicator)
        adDetailLayout = view.findViewById(R.id.ad_detail_layout)
        adDetailLayout.visibility = View.GONE
        adDetailLoadingLayout = view.findViewById(R.id.loading_layout)
        adDetailLoadingLayout.visibility = View.VISIBLE
        loadingAnimation = view.findViewById(R.id.loading)
        adErrorLayout = view.findViewById(R.id.error_layout)
        adErrorMessageTextView = view.findViewById(R.id.tv_error_message)
        loadingAnimation.playAnimation()
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
                    handleLoadingLayout()
                }

                is ApiErrorResponse -> {
                    showToast(it.errorMessage)
                    handleErrorLayout(it.toString())
                }
                is ApiEmptyResponse -> {
                    showToast(it.toString())
                    handleErrorLayout(it.toString())
                }
            }
        })
    }

    private fun handleLoadingLayout() {
        loadingAnimation.pauseAnimation()
        adDetailLoadingLayout.visibility = View.GONE
        adDetailLayout.visibility = View.VISIBLE
    }

    private fun handleErrorLayout(msg: String) {
        adDetailLoadingLayout.visibility = View.GONE
        adDetailLayout.visibility = View.GONE
        adErrorLayout.visibility = View.VISIBLE
        adErrorMessageTextView.text = getString(R.string.error_message, msg)
    }

    fun launchMap(adDetail: AdDetail) {
        val uri: String =
            java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", adDetail.location.latitude, adDetail.location.longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))

        if (isPackageAvailable(intent)) {
            startActivity(intent)
        } else {
            showToast(getString(R.string.application_not_available))
        }
    }

    fun launchEmail(adDetail: AdDetail) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto",adDetail.email, null))
        intent.type = "plain/text"
        intent.data = Uri.parse("mailto:")
        if(isPackageAvailable(intent)) {
            startActivity(Intent.createChooser(intent, "Send email..."))
        } else {
            showToast(getString(R.string.application_not_available))
        }

    }

    private fun isPackageAvailable(intent: Intent) : Boolean {
        val packageManager = activity?.packageManager
        if (packageManager?.let { intent.resolveActivity(it) } != null) {
            return true
        }
        return false
    }
}