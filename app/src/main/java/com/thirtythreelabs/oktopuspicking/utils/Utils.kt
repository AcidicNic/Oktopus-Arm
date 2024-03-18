package com.thirtythreelabs.oktopuspicking.utils

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import java.util.Locale

object Utils {

    fun capitalizeWords(str: String): String {
        return str.split(" ").joinToString(" ") {
            it.capitalized()
        }
    }

    /**
     * Replacement for Kotlin's deprecated `capitalize()` function.
     */
    fun String.capitalized(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }


//    fun getToast(context: Context, message: String, duration: Int, color: Color): Toast? {
//        // TODO: fix this and use it
//        val toast = Toast.makeText(context, message, duration)
//        if (toast.view != null) {
//            toast.view!!.setBackgroundColor(Color.RED)
//        }
//        return toast
//    }
}