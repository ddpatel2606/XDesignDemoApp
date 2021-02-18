package com.dixitpatel.xdesigndemoapp

import android.app.Application

/**
 *  Main Application class.
 */
class MyApp : Application() {

    companion object{

        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}