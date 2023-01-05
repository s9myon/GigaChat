package com.shudss00.gigachat.app

import android.app.Application
import com.shudss00.gigachat.di.AppComponent
import com.shudss00.gigachat.di.DaggerAppComponent
import timber.log.Timber

class App: Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
            .factory()
            .create(applicationContext)

        setUpTimber()
    }

    private fun setUpTimber() {
        Timber.plant(Timber.DebugTree())
    }
}