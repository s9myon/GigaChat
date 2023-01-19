package com.shudss00.gigachat.presentation.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
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
    private val binding by viewBinding(ActivityMessengerBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initUI() {
        setUpMessageListRecyclerView()
        setUpEmojiBottomSheetFragmentListener()
        setUpSendMessageOnClickListener()
        setOnMessageBoxTextChangeListener()
        presenter.setTitles(
            streamTitle = intent.getStringExtra(ARG_STREAM_TITLE).orEmpty(),
            topicTitle = intent.getStringExtra(ARG_TOPIC_TITLE).orEmpty()
        )
        presenter.onCreate()
    }

    override fun onChangeMessageState(item: MessageItem) {
        Timber.d("MessengerActivity::onChangeMessageState: $item")
    }

    override fun showMessageList(items: List<MessageItem>) {
        binding.progressBarMessageList.root.hide()
        binding.errorViewMessageList.hide()
        binding.recyclerViewMessageList.show()
        messengerAdapter.messages = items
    }

    override fun showErrorToast(text: Int) {
        showToast(text)
    }


    override fun showPagingLoading() {
        Timber.d("MessengerActivity::showPagingLoading")
    }

    override fun showFullscreenError() {
        binding.progressBarMessageList.root.hide()
        binding.errorViewMessageList.show()
        binding.recyclerViewMessageList.hide()
        binding.errorViewMessageList.setErrorButton(R.string.error_try_again) {
            presenter.onTryAgainClicked()
        }
    }

    override fun showFullscreenLoading() {
        binding.progressBarMessageList.root.show()
        binding.errorViewMessageList.hide()
        binding.recyclerViewMessageList.hide()
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

    private fun setUpMessageListRecyclerView() {
        messengerAdapter = MessengerAdapter(this)
        binding.recyclerViewMessageList.apply {
            adapter = messengerAdapter
            layoutManager = LinearLayoutManager(this@MessengerActivity)
        }
    }

    private fun setOnMessageBoxTextChangeListener() {
        with(binding) {
            editTextMessageBox.doAfterTextChanged { message ->
                if (message.toString().isBlank()) {
                    buttonSendMessage.setImageResource(R.drawable.ic_add_file)
                } else {
                    buttonSendMessage.setImageResource(R.drawable.ic_send_message)
                }
            }
        }
    }

    private fun setUpSendMessageOnClickListener() {
        with(binding) {
            buttonSendMessage.setOnClickListener {
                val message = editTextMessageBox.text.toString()
                if (message.isNotBlank()) {
                    presenter.sendMessage(message)
                    editTextMessageBox.text.clear()
                }
            }
        }
    }

    companion object {
        const val ARG_STREAM_TITLE = "ARG_STREAM_TITLE"
        const val ARG_TOPIC_TITLE = "ARG_TOPIC_TITLE"

        fun createIntent(context: Context, streamTitle: String, topicTitle: String): Intent {
            return Intent(context, MessengerActivity::class.java)
                .putExtra(ARG_STREAM_TITLE, streamTitle)
                .putExtra(ARG_TOPIC_TITLE, topicTitle)
        }
    }
}