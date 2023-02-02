package com.shudss00.gigachat.presentation.messenger

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.databinding.FragmentMessengerBinding
import com.shudss00.gigachat.domain.model.Message
import com.shudss00.gigachat.presentation.base.MvpFragment
import com.shudss00.gigachat.presentation.extensions.argument
import com.shudss00.gigachat.presentation.extensions.hide
import com.shudss00.gigachat.presentation.extensions.show
import com.shudss00.gigachat.presentation.messenger.emoji.EmojiBottomSheetFragment
import com.shudss00.gigachat.presentation.messenger.list.DateDecoration
import com.shudss00.gigachat.presentation.messenger.list.MessengerAdapter
import com.shudss00.gigachat.presentation.messenger.listitems.MessengerItem
import com.shudss00.gigachat.presentation.widget.MessageView
import timber.log.Timber
import javax.inject.Inject

class MessengerFragment : MvpFragment<MessengerView, MessengerPresenter>(R.layout.fragment_messenger),
    MessengerView, MessageView.MessageClickListener {

    @Inject
    override lateinit var presenter: MessengerPresenter
    override val mvpView: MessengerView = this
    private lateinit var messengerAdapter: MessengerAdapter
    private val binding by viewBinding(FragmentMessengerBinding::bind)
    private var streamTitle by argument("ARG_STREAM_TITLE", "")
    private val topicTitle by argument("ARG_TOPIC_TITLE", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initUI() {
        setUpToolbar()
        setUpMessageListRecyclerView()
        setUpEmojiBottomSheetFragmentListener()
        setUpSendMessageOnClickListener()
        setUpTitles()
        setOnMessageBoxTextChangeListener()
        presenter.onCreate()
    }

    override fun onChangeMessageState(item: Message) {
        Timber.d("MessengerActivity::onChangeMessageState: $item")
    }

    override fun showMessageList(items: List<MessengerItem>) {
        binding.progressBarMessageList.root.hide()
        binding.errorViewMessageList.hide()
        binding.recyclerViewMessageList.show()
        messengerAdapter.messengerItems = items
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
        EmojiBottomSheetFragment.show(childFragmentManager, messageId)
    }

    override fun onAddReactionButtonClick(messageId: Long) {
        EmojiBottomSheetFragment.show(childFragmentManager, messageId)
    }

    override fun onReactionClick(messageId: Long, emoji: Emoji) {
        presenter.setReactionToMessage(messageId, emoji)
    }

    private fun setUpEmojiBottomSheetFragmentListener() {
        EmojiBottomSheetFragment.setUpResultListener(childFragmentManager, this) { messageId, emoji ->
            presenter.setReactionToMessage(messageId, emoji)
        }
    }

    private fun setUpMessageListRecyclerView() {
        messengerAdapter = MessengerAdapter(this)
        binding.recyclerViewMessageList.apply {
            adapter = messengerAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DateDecoration())
        }
    }

    private fun setUpTitles() {
        presenter.setTitles(
            streamTitle = streamTitle,
            topicTitle = topicTitle
        )
        with(binding) {
            toolbarMessenger.title = getString(
                R.string.textView_streamTitle,
                streamTitle
            )
            textViewTopicTitle.text = getString(
                R.string.textView_topicTitle,
                topicTitle
            )
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

    private fun setUpToolbar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarMessenger) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = insets.top
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    companion object {
        private const val ARG_STREAM_TITLE = "ARG_STREAM_TITLE"
        private const val ARG_TOPIC_TITLE = "ARG_TOPIC_TITLE"

        fun newInstance(streamTitle: String, topicTitle: String) =
            MessengerFragment().apply {
                arguments = bundleOf(
                    ARG_STREAM_TITLE to streamTitle,
                    ARG_TOPIC_TITLE to topicTitle
                )
            }
    }
}