package com.shudss00.gigachat.presentation.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.databinding.ActivityMessengerBinding
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.presentation.base.MvpActivity
import com.shudss00.gigachat.presentation.messenger.list.MessengerAdapter
import timber.log.Timber
import javax.inject.Inject

class MessengerActivity : MvpActivity<MessengerView, MessengerPresenter>(R.layout.activity_messenger), MessengerView {

    @Inject
    override lateinit var presenter: MessengerPresenter
    override var mvpView: MessengerView = this
    lateinit var messengerAdapter: MessengerAdapter
    private lateinit var binding: ActivityMessengerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        presenter.getMessages(
            streamTitle = intent.getStringExtra(STREAM_TITLE).orEmpty(),
            topicTitle = intent.getStringExtra(TOPIC_TITLE).orEmpty()
        )
    }

    override fun onChangeMessageState(item: MessageItem) {
        Timber.d("MessengerActivity::onChangeMessageState: $item")
    }

    override fun showMessageList(items: List<MessageItem>) {
        Timber.d("MessengerActivity::showMessageList: $items")
        messengerAdapter.messages = items
    }

    override fun showErrorToast() {
        Timber.d("MessengerActivity::showErrorToast")
    }

    override fun showPagingLoading() {
        Timber.d("MessengerActivity::showPagingLoading")
    }

    override fun showFullscreenError() {
        Timber.d("MessengerActivity::showFullscreenError")
    }

    override fun showFullscreenLoading() {
        Timber.d("MessengerActivity::showFullscreenLoading")

    }

    private fun setUpRecyclerView() {
        messengerAdapter = MessengerAdapter()
        binding.recyclerViewMessageList.apply {
            adapter = messengerAdapter
            layoutManager = LinearLayoutManager(this@MessengerActivity)
        }
    }

    companion object {
        const val STREAM_TITLE = "STREAM_TITLE"
        const val TOPIC_TITLE = "TOPIC_TITLE"

        fun createIntent(context: Context, streamTitle: String, topicTitle: String): Intent {
            return Intent(context, MessengerActivity::class.java)
                .putExtra(STREAM_TITLE, streamTitle)
                .putExtra(TOPIC_TITLE, topicTitle)
        }
    }
}