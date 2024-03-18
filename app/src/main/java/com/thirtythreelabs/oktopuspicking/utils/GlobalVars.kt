package com.thirtythreelabs.oktopuspicking.utils

object GlobalVars {
    var user: User? = null
    var headers: Array<Header> = emptyArray()
    var items: Array<Item> = emptyArray()
    var selectedItem: Int = 0
    var selectedOrder: Int? = null

    fun clear() {
        user = null
        headers = emptyArray()
        selectedItem = 0
    }
}
