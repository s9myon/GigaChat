package com.shudss00.gigachat.presentation.mainmenu

import dagger.Module
import dagger.Provides

@Module
class MainMenuModule {
    @Provides
    fun providePresenter(): MainMenuPresenter {
        return MainMenuPresenter()
    }
}