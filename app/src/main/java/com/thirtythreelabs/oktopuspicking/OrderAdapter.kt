package com.thirtythreelabs.oktopuspicking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.thirtythreelabs.oktopuspicking.utils.Header

class OrderAdapter(context: Context, private val resource: Int, private val headers: List<Header>) : ArrayAdapter<Header>(context, resource, headers) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val orderNumberTextView: TextView = view.findViewById(R.id.)
        val orderDateTextView: TextView = view.findViewById(R.id.)
        val vendorNameTextView: TextView = view.findViewById(R.id.)
        val customerNameTextView: TextView = view.findViewById(R.id.)

        val order = headers[position]

        orderNumberTextView.text = order.orderNumber
        orderDateTextView.text = order.orderDate
        vendorNameTextView.text = order.vendorName
        customerNameTextView.text = order.customerName

        return view
    }
}