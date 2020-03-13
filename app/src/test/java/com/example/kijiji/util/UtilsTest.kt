package com.example.kijiji.util

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UtilsTest {

    private lateinit var utils: Utils

    @Before
    fun setUp() {
        utils = Utils()
    }

    @Test
    fun test_empty_or_null_string() {
        val testString = ""
        check(utils.isTextNullOrEmpty(testString))
    }

    @Test
    fun test_non_empty_string() {
        val testString = "this is test string"
        check(!utils.isTextNullOrEmpty(testString))
    }

    @Test
    fun test_dd_MMM_yyyy_date_format() {
        val inputDate = "2019-10-08T02:28:28Z"
        val expectedDate = "08-Oct-2019"
        Assert.assertEquals(expectedDate, utils.getFormatedDate(inputDate))
    }

}