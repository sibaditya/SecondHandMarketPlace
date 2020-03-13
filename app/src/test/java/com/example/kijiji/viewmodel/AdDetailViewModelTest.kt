package com.example.kijiji.viewmodel

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class AdDetailViewModelTest{
    private lateinit var adDetailViewModel: AdDetailViewModel
    private var mockWebServer = MockWebServer()
    private lateinit var baseUrl: String

    @Before
    fun setup(){
        adDetailViewModel = AdDetailViewModel()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/").toString()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Throws(Exception::class)
    fun readTestAsset(): String? {
        return ClassLoader.getSystemResource("ad_detail_sucess.json").readText()
    }

    @Test
    fun testFilePresent() {
        val file: String? = readTestAsset()
        Assert.assertThat(file, CoreMatchers.notNullValue())
    }

    @Test
    fun test_ad_detail_screen_response_not_null() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(readTestAsset() as String)
        mockWebServer.enqueue(mockResponse)
        val response = adDetailViewModel.getAddDeatil("1")
        check(response != null)
    }

}