package com.thirtythreelabs.oktopuspicking.utils

object GlobalVars {
    var user: User? = null
    var headers: Array<Header> = emptyArray()

    fun clear() {
        user = null
        headers = emptyArray()
    }
}
