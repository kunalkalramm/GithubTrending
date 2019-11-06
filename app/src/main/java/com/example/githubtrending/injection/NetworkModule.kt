package com.example.githubtrending.injection

import android.content.Context
import android.net.ConnectivityManager
import com.example.githubtrending.utils.NetworkHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val networkModule = module {
    single { NetworkHelper(get()) }
    single { androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
}