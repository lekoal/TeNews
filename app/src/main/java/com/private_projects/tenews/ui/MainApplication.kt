package com.private_projects.tenews.ui

import android.app.Application
import com.private_projects.tenews.di.allnews.allNewsKoinModule
import com.private_projects.tenews.di.ferra.ferraKoinModule
import com.private_projects.tenews.di.ixbt.ixbtKoinModule
import com.private_projects.tenews.di.main.mainKoinModule
import com.private_projects.tenews.di.tdnews.tdNewsKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                mainKoinModule,
                ixbtKoinModule,
                allNewsKoinModule,
                ferraKoinModule,
                tdNewsKoinModule
            )
        }
    }
}