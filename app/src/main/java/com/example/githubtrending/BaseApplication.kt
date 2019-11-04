package com.example.githubtrending

import android.app.Application
import com.example.githubtrending.injection.databaseModule
import com.example.githubtrending.injection.networkModule
import com.example.githubtrending.injection.repositoryModule
import com.example.githubtrending.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    companion object {
        var baseApplication: BaseApplication? = null;
        fun getInstance(): BaseApplication {
            return baseApplication!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        baseApplication = this

        startKoin {

            androidContext(this@BaseApplication)

            modules(listOf(
                networkModule,
                databaseModule,
                viewModelModule,
                repositoryModule)
            )
        }
    }
}