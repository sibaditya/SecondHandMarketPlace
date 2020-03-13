package com.example.kijiji.viewmodel

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class AdListingViewModelTest{
    private lateinit var adListingViewModel: AdListingViewModel
    private var mockWebServer = MockWebServer()
    private lateinit var baseUrl: String

    @Before
    fun setup(){
        adListingViewModel = AdListingViewModel()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/").toString()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Throws(Exception::class)
    fun readTestAsset(): String? {
        return ClassLoader.getSystemResource("success.json").readText()
    }

    @Test
    fun testFilePresent() {
        val file: String? = readTestAsset()
        Assert.assertThat(file, CoreMatchers.notNullValue())
    }

    @Test
    fun test_ad_list_screen_response_not_null() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(readTestAsset() as String)
        mockWebServer.enqueue(mockResponse)
        val response = adListingViewModel.getNextAdResponse("")
        check(response != null)
    }

}