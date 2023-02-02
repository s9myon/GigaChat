package com.shudss00.gigachat.presentation.main

import com.shudss00.gigachat.presentation.base.MvpView

interface MainView : MvpView {

    fun selectBottomNavigationItem(itemId: Int)
}