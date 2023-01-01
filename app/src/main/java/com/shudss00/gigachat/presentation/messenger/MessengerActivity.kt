package com.shudss00.gigachat.presentation.messenger

import android.os.Bundle
import com.shudss00.gigachat.presentation.base.MvpActivity

class MessengerActivity : MvpActivity<MessengerView, MessengerPresenter>(), MessengerView {

    override var mvpView: MessengerView = this
    override lateinit var presenter: MessengerPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}