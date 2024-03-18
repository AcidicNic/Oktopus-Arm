package com.thirtythreelabs.oktopuspicking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thirtythreelabs.oktopuspicking.utils.API
import com.thirtythreelabs.oktopuspicking.utils.Config
import com.thirtythreelabs.oktopuspicking.utils.GlobalVars
import com.thirtythreelabs.oktopuspicking.utils.Header
import com.thirtythreelabs.oktopuspicking.utils.HeaderItemArticle
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray

class OrdersActivity : AppCompatActivity() {

    private var logTag = "OrdersActivity"

    private lateinit var totalOrdersTxt: TextView
    private lateinit var loadingSpinner: ProgressBar
    private lateinit var noOrdersFoundTxt: TextView
    private lateinit var usernameTxt: TextView
    private lateinit var exitBtn: Button
    private lateinit var refreshBtn: Button
    private lateinit var closedOrdersBtn: Button
    private lateinit var ordersListView: ListView

    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        totalOrdersTxt = findViewById(R.id.total_orders)
        loadingSpinner = findViewById(R.id.loading_spinner)
        noOrdersFoundTxt = findViewById(R.id.no_orders_msg)
        usernameTxt = findViewById(R.id.username)
        exitBtn = findViewById(R.id.exit)
        refreshBtn = findViewById(R.id.refresh)
        closedOrdersBtn = findViewById(R.id.closed_orders)
        ordersListView = findViewById(R.id.orders_list)

        loading(enabled=true)

        lifecycleScope.launch {
            Log.d(logTag, "Making get headers request...")
            val newHeaders = async { getHeaders() }.await()
            GlobalVars.headers = newHeaders?: emptyArray()

            if (GlobalVars.headers.isNotEmpty()) {
                orderAdapter = OrderAdapter(
                    this@OrdersActivity, R.layout.order, GlobalVars.headers.toList())
                ordersListView.adapter = orderAdapter

                totalOrdersTxt.text = GlobalVars.headers.size.toString()
                usernameTxt.text = GlobalVars.user!!.username

                loading(enabled=false, ordersFound=true)
            } else {
                Log.e(logTag, "No headers found.")
                loading(enabled=false, ordersFound=false)
            }
        }

        exitBtn.setOnClickListener {
            GlobalVars.clear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        closedOrdersBtn.setOnClickListener {

        }

        refreshBtn.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun loading(enabled: Boolean, ordersFound: Boolean = true) {
        if (enabled) {
            loadingSpinner.visibility = View.VISIBLE
            noOrdersFoundTxt.visibility = View.GONE
            ordersListView.visibility = View.GONE
        } else {
            loadingSpinner.visibility = View.GONE
            if (ordersFound) {
                noOrdersFoundTxt.visibility = View.GONE
                ordersListView.visibility = View.VISIBLE
            } else {
                ordersListView.visibility = View.GONE
                noOrdersFoundTxt.visibility = View.VISIBLE
            }
        }
    }

    private suspend fun getHeaders(): Array<Header>? {
        if (GlobalVars.user == null) {
            Log.e(logTag, "User is null")
            return null
        }

        val jsonRes = API.handlePostRequest(
            mapOf(
                "CompanyId" to Config.COMPANY_ID,
                "HeaderDate" to Config.HEADER_DATE,
                "JLocId" to Config.JLOC_ID,
                "HeaderStatusId" to Config.HEADER_STATUS_ID,
                "ArmId" to GlobalVars.user!!.armId,
                "StatusId" to "R",
                "Tipo" to "I",
            ),
            "GET_HEADERS")

        if (jsonRes == null) {
            return null
        }
        try {
            jsonRes.getJSONArray("Headers").let {
                val list = mutableListOf<Header>()
                for (i in 0 until it.length()) {
                    list.add(Header(it.getJSONObject(i)))
                }
                return list.toTypedArray()
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error parsing Headers JSON: $e")
            return null
        }
    }

}
