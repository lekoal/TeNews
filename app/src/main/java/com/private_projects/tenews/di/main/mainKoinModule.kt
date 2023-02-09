package com.private_projects.tenews.di.main

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.ui.main.MainActivity
import com.private_projects.tenews.ui.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainKoinModule = module {
    single(named("base_url")) { VkHelpData.BASE_URL }
    factory<Retrofit>(named("retrofit")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named("base_url")))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    scope<MainActivity> {
        scoped(named("main_view_model")) {
            MainViewModel()
        }
    }
}