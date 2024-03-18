package com.thirtythreelabs.oktopuspicking.utils

import org.json.JSONObject

class Header (
    var HeaderId: String = "",
    var HeaderDate: String = "",
    var HeaderStatusId: String = "",
    var HeaderStatusDescription: String = "",
    var HeaderPriorityId: Int = 0,
    var HeaderPriorityDescription: String = "",
    var DeliveryTypeId: Int = 0,
    var DeliveryTypeDescription: String = "",
    var SalesmanId: Int = 0,
    var SalesmanName: String = "",
    var CustomerId: Int = 0,
    var CustomerName: String = "",
    var HeaderNotes: String = "",
    var TotalLines: Int = 0,
    var TotalLinesReadyForPickup: Int = 0,
    var TotalLinesPicked: Int = 0,
    var WareHouseId: Int = 0,
    var HeaderHour: String = "",
    var HeaderItemsDiffToPick: Int = 0,
    var TotalLinesPaused: Int = 0,
    var HeaderDateFact: String = "",
    var HeaderListArticles: Array<HeaderItemArticle> = emptyArray(),
    var HeaderSolMer: String = "",
    var HEnvPrio: Int = 0,
    var PedHTComId: String = "",
    var JLocIdGen: Int = 0,
    var HeaderRefId: String = "",

    var TotalBultos: Int = 0,
    var HabEntInm: String = "",
    var HeaderPedId: String = "",
) {
    constructor(json: JSONObject) : this() {
        HeaderId = json.optString("HeaderId")
        HeaderDate = json.optString("HeaderDate")
        HeaderStatusId = json.optString("HeaderStatusId")
        HeaderStatusDescription = json.optString("HeaderStatusDescription")
        HeaderPriorityId = json.optInt("HeaderPriorityId")
        HeaderPriorityDescription = json.optString("HeaderPriorityDescription")
        DeliveryTypeId = json.optInt("DeliveryTypeId")
        DeliveryTypeDescription = json.optString("DeliveryTypeDescription")
        SalesmanId = json.optInt("SalesmanId")
        SalesmanName = json.optString("SalesmanName")
        CustomerId = json.optInt("CustomerId")
        CustomerName = json.optString("CustomerName")
        HeaderNotes = json.optString("HeaderNotes")
        TotalLines = json.optInt("TotalLines")
        TotalLinesReadyForPickup = json.optInt("TotalLinesReadyForPickup")
        TotalLinesPicked = json.optInt("TotalLinesPicked")
        WareHouseId = json.optInt("WareHouseId")
        HeaderHour = json.optString("HeaderHour")
        HeaderItemsDiffToPick = json.optInt("HeaderItemsDiffToPick")
        TotalLinesPaused = json.optInt("TotalLinesPaused")
        HeaderDateFact = json.optString("HeaderDateFact")
        HeaderSolMer = json.optString("HeaderSolMer")
        HEnvPrio = json.optInt("HEnvPrio")
        PedHTComId = json.optString("PedHTComId")
        JLocIdGen = json.optInt("JLocIdGen")
        HeaderRefId = json.optString("HeaderRefId")
        TotalBultos = json.optInt("TotalBultos")
        HabEntInm = json.optString("HabEntInm")
        HeaderPedId = json.optString("HeaderPedId")

        json.optJSONArray("HeaderListArticles")?.let {
            val list = mutableListOf<HeaderItemArticle>()
            for (i in 0 until it.length()) {
                list.add(HeaderItemArticle(it.getJSONObject(i)))
            }
            HeaderListArticles = list.toTypedArray()
        }
    }
}
