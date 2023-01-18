package com.shudss00.gigachat.presentation.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.databinding.ActivityMessengerBinding
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.presentation.base.MvpActivity
import com.shudss00.gigachat.presentation.extensions.hide
import com.shudss00.gigachat.presentation.extensions.show
import com.shudss00.gigachat.presentation.messenger.emoji.EmojiBottomSheetFragment
import com.shudss00.gigachat.presentation.messenger.list.MessengerAdapter
import com.shudss00.gigachat.presentation.widget.MessageView
import timber.log.Timber
import javax.inject.Inject

class MessengerActivity : MvpActivity<MessengerView, MessengerPresenter>(R.layout.activity_messenger),
    MessengerView, MessageView.MessageClickListener {

    @Inject
    override lateinit var presenter: MessengerPresenter
    override var mvpView: MessengerView = this
    private lateinit var messengerAdapter: MessengerAdapter
    private lateinit var binding: ActivityMessengerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpEmojiBottomSheetFragmentListener()
        setUpRecyclerView()
        setUpSendMessageOnClickListener()
        setUpSwipeRefreshLayout()
        setUpInitialState()
    }

    override fun onChangeMessageState(item: MessageItem) {
        Timber.d("MessengerActivity::onChangeMessageState: $item")
    }

    override fun showMessageList(items: List<MessageItem>) {
        binding.progressBarMessageList.root.hide()
        binding.errorViewMessageList.hide()
        binding.swipeRefreshLayoutMessageList.show()
        messengerAdapter.messages = items
    }

    override fun showErrorToast() {
        showToast(R.string.error_failed_load_data)
    }

    override fun showPagingLoading() {
        Timber.d("MessengerActivity::showPagingLoading")
    }

    override fun showFullscreenError() {
        binding.progressBarMessageList.root.hide()
        binding.errorViewMessageList.show()
        binding.swipeRefreshLayoutMessageList.hide()
        binding.errorViewMessageList.setErrorButton(R.string.error_try_again) {
            presenter.onTryAgainClicked()
        }
    }

    override fun showFullscreenLoading() {
        binding.progressBarMessageList.root.show()
        binding.errorViewMessageList.hide()
        binding.swipeRefreshLayoutMessageList.hide()
    }

    override fun hideSwipeRefresh() {
        binding.swipeRefreshLayoutMessageList.isRefreshing = false
    }

    override fun onMessageLongClick(messageId: Long) {
        EmojiBottomSheetFragment.show(supportFragmentManager, messageId)
    }

    override fun onAddReactionButtonClick(messageId: Long) {
        EmojiBottomSheetFragment.show(supportFragmentManager, messageId)
    }

    override fun onReactionClick(messageId: Long, emoji: Emoji) {
        presenter.setReactionToMessage(messageId, emoji)
    }

    private fun setUpEmojiBottomSheetFragmentListener() {
        EmojiBottomSheetFragment.setUpResultListener(supportFragmentManager, this) { messageId, emoji ->
            presenter.setReactionToMessage(messageId, emoji)
        }
    }

    private fun setUpRecyclerView() {
        messengerAdapter = MessengerAdapter(this)
        binding.recyclerViewMessageList.apply {
            adapter = messengerAdapter
            layoutManager = LinearLayoutManager(this@MessengerActivity)
        }
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayoutMessageList.setOnRefreshListener {
            presenter.onSwipeToRefreshTriggered()
        }
    }

    private fun setUpInitialState() {
        presenter.setTitles(
            streamTitle = intent.getStringExtra(STREAM_TITLE).orEmpty(),
            topicTitle = intent.getStringExtra(TOPIC_TITLE).orEmpty()
        )
        presenter.onCreate()
    }

    private fun setUpSendMessageOnClickListener() {
        binding.buttonSendMessage.setOnClickListener {
            presenter.sendMessage(
                binding.editTextMessageBox.text.toString()
            )
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