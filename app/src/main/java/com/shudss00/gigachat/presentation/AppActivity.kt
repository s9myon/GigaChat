package com.shudss00.gigachat.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.main.MainFragment
import com.shudss00.gigachat.presentation.messenger.MessengerFragment

class AppActivity : AppCompatActivity(R.layout.layout_container) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            openMessengerFragment()
        }
    }

    private fun openMessengerFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MainFragment>(R.id.fragmentContainerView_container)
        }
    }
}