package com.thirtythreelabs.oktopuspicking.utils

import org.json.JSONObject

class HeaderItemArticle(
    var ItemId: String = "",
    var UnitId: Int = 0,
    var ItemQuantityPicked: String = "",
    var ItemQuantity: String = "",
) {
    constructor(json: JSONObject) : this() {
        ItemId = json.optString("ItemId")
        UnitId = json.optInt("UnitId")
        ItemQuantityPicked = json.optString("ItemQuantityPicked")
        ItemQuantity = json.optString("ItemQuantity")
    }
}
