package com.shudss00.gigachat.presentation.main

import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun providePresenter(
        navigationItemsHelper: NavigationItemsHelper
    ): MainPresenter {
        return MainPresenter(navigationItemsHelper)
    }
}