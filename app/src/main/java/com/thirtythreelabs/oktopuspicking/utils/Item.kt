package com.thirtythreelabs.oktopuspicking.utils

import org.json.JSONObject

class Item (
    var HeaderId: String = "",
    var LineId: String = "",
    var ItemId: String = "",
    var ItemDescription: String = "",
    var ItemHowToRead: String = "",
    var ItemDescriptionHowtoRead: String = "",
    var ItemInventory: String = "",
    var BrandId: String = "",
    var BrandName: String = "",
    var ItemImage: String = "",
    var UnitId: String = "",
    var UnitDescription: String = "",
    var ItemOrigin: String = "",
    var ItemQuantity: String = "",
    var ItemStatusId: String = "",
    var ItemStatusDescription: String = "",
    var ItemLastOperatorId: String = "",
    var ItemLastOperatorName: String = "",
    var ItemXCoord: String = "",
    var ItemYCoord: String = "",
    var ItemZCoord: String = "",
    var ItemSupplierId: String = "",
    var ItemSupplierName: String = "",
    var ItemQuantityPicked: String = "",
    var BrandNameHowToRead: String = "",
    var ItemsSupplierNameHowToRead: String = "",
    var ItemDifficultToPick: String = "",
    var ItemFromSuc1: String = "",
    var ItemFromSuc2: String = ""
) {
    constructor(json: JSONObject) : this() {
        HeaderId = json.optString("HeaderId")
        LineId = json.optString("LineId")
        ItemId = json.optString("ItemId")
        ItemDescription = json.optString("ItemDescription")
        ItemHowToRead = json.optString("ItemHowToRead")
        ItemDescriptionHowtoRead = json.optString("ItemDescriptionHowtoRead")
        ItemInventory = json.optString("ItemInventory")
        BrandId = json.optString("BrandId")
        BrandName = json.optString("BrandName")
        ItemImage = json.optString("ItemImage")
        UnitId = json.optString("UnitId")
        UnitDescription = json.optString("UnitDescription")
        ItemOrigin = json.optString("ItemOrigin")
        ItemQuantity = json.optString("ItemQuantity")
        ItemStatusId = json.optString("ItemStatusId")
        ItemStatusDescription = json.optString("ItemStatusDescription")
        ItemLastOperatorId = json.optString("ItemLastOperatorId")
        ItemLastOperatorName = json.optString("ItemLastOperatorName")
        ItemXCoord = json.optString("ItemXCoord")
        ItemYCoord = json.optString("ItemYCoord")
        ItemZCoord = json.optString("ItemZCoord")
        ItemSupplierId = json.optString("ItemSupplierId")
        ItemSupplierName = json.optString("ItemSupplierName")
        ItemQuantityPicked = json.optString("ItemQuantityPicked")
        BrandNameHowToRead = json.optString("BrandNameHowToRead")
        ItemsSupplierNameHowToRead = json.optString("ItemsSupplierNameHowToRead")
        ItemDifficultToPick = json.optString("ItemDifficultToPick")
        ItemFromSuc1 = json.optString("ItemFromSuc1")
        ItemFromSuc2 = json.optString("ItemFromSuc2")
    }
}
