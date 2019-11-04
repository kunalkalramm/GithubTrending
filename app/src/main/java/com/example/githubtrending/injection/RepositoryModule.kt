package com.example.githubtrending.injection

import com.example.githubtrending.repository.TopRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        TopRepository(get(), get())
    }
}
