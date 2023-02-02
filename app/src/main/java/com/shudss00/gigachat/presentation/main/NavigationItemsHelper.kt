package com.shudss00.gigachat.presentation.main

import androidx.annotation.IdRes
import androidx.fragment.app.*
import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.streamlist.StreamListFragment
import com.shudss00.gigachat.presentation.userlist.UserListFragment
import com.shudss00.gigachat.presentation.userprofile.UserProfileFragment
import javax.inject.Inject

data class NavigationItem(
    val itemId: Int,
    val fragment: Fragment,
    val fragmentTag: String
)

class NavigationItemsHelper @Inject constructor() {

    private val navigationItems = setOf(
        NavigationItem(
            itemId = R.id.item_streamList,
            fragment = StreamListFragment.newInstance(),
            fragmentTag = StreamListFragment.TAG
        ),
        NavigationItem(
            itemId = R.id.item_userList,
            fragment = UserListFragment.newInstance(),
            fragmentTag = UserListFragment.TAG
        ),
        NavigationItem(
            itemId = R.id.item_profile,
            fragment = UserProfileFragment.newInstance(),
            fragmentTag = UserProfileFragment.TAG
        )
    )

    val itemStack = linkedSetOf(navigationItems.first().fragmentTag)

    fun restoreStack(stack: Collection<String>) {
        itemStack.clear()
        itemStack.addAll(stack)
    }

    fun onNavigationItemSelected(
        fragmentManager: FragmentManager,
        @IdRes itemId: Int,
        @IdRes containerId: Int
    ): Boolean {
        val nextItem = navigationItems.first { it.itemId == itemId }
        val previousItem = itemStack.lastOrNull()
        if (previousItem != null) {
            fragmentManager.saveBackStack(previousItem)
            itemStack.add(nextItem.fragmentTag)

            fragmentManager.commit {
                setReorderingAllowed(true)
                replace(containerId, nextItem.fragment)
            }

            fragmentManager.restoreBackStack(nextItem.fragmentTag)
        } else {

        }
        return true
    }

    fun getItemIdByTag(fragmentTag: String): Int {
        return navigationItems.first { it.fragmentTag == fragmentTag }.itemId
    }
}

