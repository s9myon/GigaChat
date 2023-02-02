package com.shudss00.gigachat.presentation.main

import androidx.fragment.app.FragmentManager
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val navigationItemsHelper: NavigationItemsHelper
) : RxPresenter<MainView>() {

    fun getNavigationItemStack() = navigationItemsHelper.itemStack

    fun restoreStack(restoredStack: ArrayList<String>) {
        navigationItemsHelper.restoreStack(restoredStack)
    }

    fun onNavigationItemSelected(
        fragmentManager: FragmentManager,
        itemId: Int,
        containerId: Int
    ): Boolean {
        return navigationItemsHelper.onNavigationItemSelected(fragmentManager, itemId, containerId)
    }

    fun onCreate() {
        view?.selectBottomNavigationItem(
            navigationItemsHelper.getItemIdByTag(navigationItemsHelper.itemStack.last())
        )
    }
}