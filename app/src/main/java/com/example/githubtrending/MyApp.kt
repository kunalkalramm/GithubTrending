package com.example.githubtrending

import android.app.Application

class MyApp : Application() {
    companion object {
        var myApp: MyApp? = null;
        fun getInstance(): MyApp {
            return myApp!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        myApp = this
    }
}