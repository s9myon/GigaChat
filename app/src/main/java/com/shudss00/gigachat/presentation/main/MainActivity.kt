package com.shudss00.gigachat.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.messenger.MessengerActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        openMessengerActivity()
    }

    private fun openMessengerActivity() {
        val intent = MessengerActivity.createIntent(
            context = this,
            streamTitle = "testChannel",
            topicTitle = "stream events"
        )
        startActivity(intent)
    }
}