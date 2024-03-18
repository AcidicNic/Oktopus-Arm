package com.thirtythreelabs.oktopuspicking.utils

class User(
    val username: String = "",
    val wareHouseId: Int = 0,
    val armId: Int = 0,
    val armNomCmpl: String = "",
    val armHabPara: String = "",
) {

    fun prettyStr(): String {
        return "User {\n\tUsername: ${username}\n\tWarehouse ID: ${wareHouseId}\n\t" +
                "Arm ID: ${armId}\n\tArm Nom Cmpl: ${armNomCmpl}\n\tArm Hab Para: ${armHabPara}\n}"
    }
}