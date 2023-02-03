package com.shudss00.gigachat.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.main.MainFragment
import com.shudss00.gigachat.presentation.messenger.MessengerFragment
import com.shudss00.gigachat.presentation.streamlist.StreamListFragment

class AppActivity : AppCompatActivity(R.layout.layout_container) {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            openMenuFragment()
        }
    }

    fun openMessengerFragment(streamTitle: String, topicTitle: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.layout_container,
                MessengerFragment.newInstance(streamTitle, topicTitle)
            )
            addToBackStack(StreamListFragment.TAG)
        }
    }

    private fun openMenuFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MainFragment>(R.id.layout_container)
        }
    }
}