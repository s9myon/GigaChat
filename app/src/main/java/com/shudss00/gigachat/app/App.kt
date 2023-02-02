package com.shudss00.gigachat.app

import android.app.Application
import com.shudss00.gigachat.di.AppComponent
import com.shudss00.gigachat.di.DaggerAppComponent
import timber.log.Timber

class App: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initAppScope()
        initTimber()
    }

    private fun initAppScope() {
        appComponent = DaggerAppComponent
            .factory()
            .create(applicationContext)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}