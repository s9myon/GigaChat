package com.shudss00.gigachat.di

import android.content.Context
import com.shudss00.gigachat.presentation.messenger.MessengerActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ImplementationBindingModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MessengerActivity)
}