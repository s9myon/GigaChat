package com.shudss00.gigachat.presentation.mainmenu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shudss00.gigachat.presentation.streamlist.StreamListFragment
import com.shudss00.gigachat.presentation.userlist.UserListFragment
import com.shudss00.gigachat.presentation.userprofile.UserProfileFragment

class MainMenuAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TAB_STREAM_LIST -> StreamListFragment()
            TAB_USER_LIST -> UserListFragment()
            TAB_USER_PROFILE -> UserProfileFragment()
            else -> StreamListFragment()
        }
    }
}