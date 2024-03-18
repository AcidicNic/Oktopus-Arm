package com.thirtythreelabs.oktopuspicking

import android.app.Application

class Oktopus : Application() {

    lateinit var name: String

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: Oktopus? = null

        fun getInstance(): Oktopus =
            instance ?: throw IllegalStateException("Oktopus is not initialized")
    }
}