package com.example.githubtrending.injection

import android.content.Context
import com.example.githubtrending.RecyclerViewRepositoryAdapter
import org.koin.dsl.module

val recyclerViewAdapterModule = module {
    factory { (context: Context) ->
        RecyclerViewRepositoryAdapter(context)
    }
}