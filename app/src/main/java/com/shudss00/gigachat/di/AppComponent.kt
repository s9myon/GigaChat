package com.shudss00.gigachat.di

import android.content.Context
import com.shudss00.gigachat.presentation.main.MainFragment
import com.shudss00.gigachat.presentation.messenger.MessengerFragment
import com.shudss00.gigachat.presentation.streamlist.StreamListFragment
import com.shudss00.gigachat.presentation.userlist.UserListFragment
import com.shudss00.gigachat.presentation.userprofile.UserProfileFragment
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
    fun inject(fragment: MessengerFragment)
    fun inject(fragment: StreamListFragment)
    fun inject(fragment: UserListFragment)
    fun inject(fragment: UserProfileFragment)
    fun inject(fragment: MainFragment)
}