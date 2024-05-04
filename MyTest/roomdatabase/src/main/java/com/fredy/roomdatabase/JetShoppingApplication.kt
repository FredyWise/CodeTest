package com.fredy.roomdatabase

import android.app.Application

class JetShoppingApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}