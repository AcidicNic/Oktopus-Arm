package com.thirtythreelabs.oktopuspicking.utils

import org.json.JSONObject

class User(
    var username: String = "",
    var wareHouseId: Int = 0,
    var armId: Int = 0,
    var armNomCmpl: String = "",
    var armHabPara: String = "",
) {
    constructor(json: JSONObject, name: String) : this() {
        username = name
        wareHouseId = json.optInt("wareHouseId")
        armId = json.optInt("armId")
        armNomCmpl = json.optString("armNomCmpl")
        armHabPara = json.optString("armHabPara")
    }

    fun prettyStr(): String {
        return "User {\n\tUsername: ${username}\n\tWarehouse ID: ${wareHouseId}\n\t" +
                "Arm ID: ${armId}\n\tArm Nom Cmpl: ${armNomCmpl}\n\tArm Hab Para: ${armHabPara}\n}"
    }
}