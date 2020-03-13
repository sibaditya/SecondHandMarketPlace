package com.example.kijiji.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.kijiji.R
import com.example.kijiji.databinding.SplashScreenFragmentBinding
import com.example.kijiji.network.ApiEmptyResponse
import com.example.kijiji.network.ApiErrorResponse
import com.example.kijiji.network.ApiSuccessResponse
import com.example.kijiji.viewmodel.SplashScreenFragmentViewModel
import com.example.kijiji.viewmodel.livedata.ResourceStatus

class SplashScreenFragment: AdParentFragment() {

    companion object {
        val TAG = SplashScreenFragment::class.java.canonicalName
        const val AD_REPONSE_KEY = "addResponse"
        const val AD_ERROR_RESPONSE = "addErrorResponse"
    }

    lateinit var navController: NavController
    lateinit var splashScreenFragmentView : SplashScreenFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val adListingFragmentBinding = DataBindingUtil.inflate<SplashScreenFragmentBinding>(
            inflater, R.layout.splash_screen_fragment, container, false)
        return adListingFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =  Navigation.findNavController(view)
        splashScreenFragmentView = ViewModelProvider(this).get(SplashScreenFragmentViewModel::class.java)
        splashScreenFragmentView.getAdResponse().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiSuccessResponse -> {
                    Log.d(TAG, it.body.toString())
                    val bundle = bundleOf(AD_REPONSE_KEY to it.body)
                    navigateToAdListingFragment(bundle)
                }
                is ApiErrorResponse -> {
                    showToast(it.errorMessage)
                    val bundle = bundleOf(AD_ERROR_RESPONSE to it.errorMessage)
                    navigateToAdListingFragment(bundle)
                }
                is ApiEmptyResponse -> {
                    showToast(it.toString())
                    val bundle = bundleOf(AD_ERROR_RESPONSE to it.toString())
                    navigateToAdListingFragment(bundle)
                }
            }
        })
    }

    private fun navigateToAdListingFragment(bundle: Bundle) {
        navController.navigate(R.id.action_splashScreenFragment_to_adListingFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }
}