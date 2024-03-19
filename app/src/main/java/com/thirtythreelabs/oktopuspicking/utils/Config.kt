package com.thirtythreelabs.oktopuspicking.utils

object Config {
    const val DEFAULT_URL: String = "http://192.168.1.92/smpm/rest/"
    var URL: String = "http://192.168.1.92/smpmt/rest/"
//    var URL: String = "http://10.0.2.2:5000/"

    val API_ENDPOINTS = mapOf(
        "LOGIN" to "/loginav1",
        "GET_HEADERS" to "/getheadersav1",
        "GET_LINES" to "/getlinesav1",
        "SET_ITEM_STATUS" to "/setitemstatusav1",
    )

    const val COMPANY_ID: String = "1"
    const val JLOC_ID: String = "1"
    const val HEADER_DATE: String = "20240101"
    const val HEADER_STATUS_ID: String = "8"

    fun updateURL(url: String) {
        if (url.isEmpty()) {
            URL = DEFAULT_URL
            return
        }
        URL = url.trimEnd('/') + '/'
    }

}
