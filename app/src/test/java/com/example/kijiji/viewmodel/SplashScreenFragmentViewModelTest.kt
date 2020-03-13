package com.example.kijiji.viewmodel

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test


class SplashScreenFragmentViewModelTest {

    private lateinit var splashScreenFragmentViewModel: SplashScreenFragmentViewModel
    private var mockWebServer = MockWebServer()
    private lateinit var baseUrl: String

    @Before
    fun setup(){
        splashScreenFragmentViewModel = SplashScreenFragmentViewModel()
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
        assertThat(file, notNullValue())
    }

    @Test
    fun test_splash_screen_success_response() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(readTestAsset() as String)
        mockWebServer.enqueue(mockResponse)
        val response = splashScreenFragmentViewModel.getAdResponse()
        check(response != null)
    }

}