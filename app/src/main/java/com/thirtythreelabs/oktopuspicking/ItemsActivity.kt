package com.thirtythreelabs.oktopuspicking

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thirtythreelabs.oktopuspicking.utils.API
import com.thirtythreelabs.oktopuspicking.utils.Config
import com.thirtythreelabs.oktopuspicking.utils.GlobalVars
import com.thirtythreelabs.oktopuspicking.utils.Item
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ItemsActivity : AppCompatActivity() {
    private var logTag = "ItemsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        lifecycleScope.launch {
            Log.d(logTag, "Making get headers request...")
            val newItems = async { getItems() }.await()
            GlobalVars.items = newItems?: emptyArray()

            if (GlobalVars.items.isNotEmpty()) {
                Log.d(logTag, "Items found: ${GlobalVars.items.size}")
                Log.d(logTag, "Items: ${GlobalVars.items}")
//                orderAdapter = OrderAdapter(
//                    this@OrdersActivity, R.layout.order, GlobalVars.headers.toList())
//                ordersListView.adapter = orderAdapter


//                loading(enabled=false, ordersFound=true)
            } else {
                Log.e(logTag, "No items found.")
//                loading(enabled=false, ordersFound=false)
            }
        }

    }

    private suspend fun getItems(): Array<Item>? {
        if (GlobalVars.selectedOrder == null || GlobalVars.selectedOrder!! < GlobalVars.headers.size) {
            Log.e(logTag, "invalid selectedOrder (${GlobalVars.selectedOrder}). Redirecting to OrdersActivity...")
            startActivity(Intent(this, OrdersActivity::class.java))
            finish()
            return null
        }

        Log.d(logTag, "Making get items request...")
        Log.d(logTag, "HeaderPedId: ${GlobalVars.headers[GlobalVars.selectedOrder!!].HeaderPedId}")


        val jsonRes = API.handlePostRequest(
            mapOf(
                "CompanyId" to Config.COMPANY_ID,
                "JFaNum" to "2843448", // ???
                "JLocId" to Config.JLOC_ID,
                "HTComID" to "013", // ???
//                "HeaderPedId" to GlobalVars.headers[GlobalVars.selectedOrder!!].HeaderPedId,
                "HeaderPedId" to "476726",
            ),
            "GET_LINES")

        if (jsonRes == null) {
            return null
        }
        try {
            jsonRes.getJSONArray("Lines").let {
                val list = mutableListOf<Item>()
                for (i in 0 until it.length()) {
                    list.add(Item(it.getJSONObject(i)))
                }
                return list.toTypedArray()
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error parsing Headers JSON: $e")
            return null
        }
    }
}
