package com.example.weatherappbook

import android.app.Application
import com.example.weatherappbook.utils.DelegatesExt

class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
        //the value of the app instance can be modified only in the App class
//        lateinit var instance: App
//        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}