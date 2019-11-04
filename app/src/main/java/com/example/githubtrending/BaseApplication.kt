package com.example.githubtrending

import android.app.Application
import com.example.githubtrending.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@BaseApplication)

            modules(listOf(
                networkModule,
                databaseModule,
                viewModelModule,
                repositoryModule,
                recyclerViewAdapterModule)
            )
        }
    }
}