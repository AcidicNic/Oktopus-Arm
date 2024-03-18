package com.thirtythreelabs.oktopuspicking.utils

import android.content.Context
import android.graphics.Color
import android.widget.Toast

object Utils {
    fun getToast(context: Context, message: String, duration: Int, color: Color): Toast? {
        val toast = Toast.makeText(context, message, duration)
        if (toast.view != null) {
            toast.view!!.setBackgroundColor(Color.RED)
        }
        return toast
    }
}