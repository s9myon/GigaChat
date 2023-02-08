package com.shudss00.gigachat.presentation.messenger

import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
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
import com.shudss00.gigachat.presentation.extensions.doOnApplyWindowInsets
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
    MessengerView {

    @Inject
    override lateinit var presenter: MessengerPresenter
    override val mvpView: MessengerView = this
    private lateinit var messengerAdapter: MessengerAdapter
    private val binding by viewBinding(FragmentMessengerBinding::bind)
    private val streamTitle by argument(ARG_STREAM_TITLE, "")
    private val topicTitle by argument(ARG_TOPIC_TITLE, "")
    private val companionUserId by argument(ARG_USER_ID, -1L)
    private val companionUsername by argument(ARG_USERNAME, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initUI() {
        setUpToolbar()
        setUpMessageListRecyclerView()
        setUpEmojiBottomSheetFragmentListener()
        setUpSendMessageButtonOnClickListener()
        setUpTitles()
        setUpMessageBox()
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

    private fun setUpEmojiBottomSheetFragmentListener() {
        EmojiBottomSheetFragment.setUpResultListener(childFragmentManager, this) { messageId, emoji ->
            presenter.setReactionToMessage(messageId, emoji)
        }
    }

    private fun setUpMessageListRecyclerView() {
        messengerAdapter = MessengerAdapter(object : MessageView.MessageClickListener {
            override fun onMessageLongClick(messageId: Long) {
                EmojiBottomSheetFragment.show(childFragmentManager, messageId)
            }

            override fun onAddReactionButtonClick(messageId: Long) {
                EmojiBottomSheetFragment.show(childFragmentManager, messageId)
            }

            override fun onReactionClick(messageId: Long, emoji: Emoji) {
                presenter.setReactionToMessage(messageId, emoji)
            }
        })
        binding.recyclerViewMessageList.apply {
            adapter = messengerAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DateDecoration())
        }
    }

    private fun setUpTitles() {
        presenter.setTitles(
            streamTitle = streamTitle,
            topicTitle = topicTitle,
            companionUserId = companionUserId
        )
        with(binding) {
            if (streamTitle.isNotBlank() && topicTitle.isNotBlank()) {
                toolbarMessenger.title = getString(
                    R.string.textView_streamTitle,
                    streamTitle
                )
                textViewTopicTitle.text = getString(
                    R.string.textView_topicTitle,
                    topicTitle
                )
                textViewTopicTitle.show()
            }
            if (companionUserId != -1L && companionUsername.isNotBlank()) {
                toolbarMessenger.title = getString(
                    R.string.textView_streamTitle,
                    companionUsername
                )
                textViewTopicTitle.hide()
            }
        }
    }

    private fun setUpMessageBox() {
        with(binding) {
            editTextMessageBox.doAfterTextChanged { message ->
                if (message.toString().isBlank()) {
                    buttonSendMessage.setImageResource(R.drawable.ic_add_file)
                } else {
                    buttonSendMessage.setImageResource(R.drawable.ic_send_message)
                }
            }
            editTextMessageBox.doOnApplyWindowInsets { view, insets, _ ->
                val systemBarInset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                val keyboardInset = insets.getInsets(WindowInsetsCompat.Type.ime())
                view.updateLayoutParams<MarginLayoutParams> {
                    bottomMargin = systemBarInset.bottom + keyboardInset.bottom
                }
                WindowInsetsCompat.CONSUMED
            }
            ViewCompat.setWindowInsetsAnimationCallback(
                editTextMessageBox,
                object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
                    var startBottom = 0f
                    var endBottom = 0f

                    override fun onPrepare(animation: WindowInsetsAnimationCompat) {
                        startBottom = editTextMessageBox.bottom.toFloat()
                    }

                    override fun onStart(
                        animation: WindowInsetsAnimationCompat,
                        bounds: WindowInsetsAnimationCompat.BoundsCompat
                    ): WindowInsetsAnimationCompat.BoundsCompat {
                        endBottom = editTextMessageBox.bottom.toFloat()
                        return bounds
                    }

                    override fun onProgress(
                        insets: WindowInsetsCompat,
                        runningAnimations: MutableList<WindowInsetsAnimationCompat>
                    ): WindowInsetsCompat {
                        val keyboardAnimation = runningAnimations.find {
                            it.typeMask and WindowInsetsCompat.Type.ime() != 0
                        } ?: return insets
                        val intermediatePosition =
                            (startBottom - endBottom) * (1 - keyboardAnimation.interpolatedFraction)
                        editTextMessageBox.translationY = intermediatePosition
                        buttonSendMessage.translationY = intermediatePosition
                        return insets
                    }

                }
            )
        }
    }

    private fun setUpSendMessageButtonOnClickListener() {
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
        with(binding) {
            toolbarMessenger.setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    companion object {
        private const val ARG_STREAM_TITLE = "ARG_STREAM_TITLE"
        private const val ARG_TOPIC_TITLE = "ARG_TOPIC_TITLE"
        private const val ARG_USER_ID = "ARG_USER_ID"
        private const val ARG_USERNAME = "ARG_USERNAME"

        fun newInstance(streamTitle: String, topicTitle: String) =
            MessengerFragment().apply {
                arguments = bundleOf(
                    ARG_STREAM_TITLE to streamTitle,
                    ARG_TOPIC_TITLE to topicTitle
                )
            }

        fun newInstance(userId: Long, username: String) =
            MessengerFragment().apply {
                arguments = bundleOf(
                    ARG_USER_ID to userId,
                    ARG_USERNAME to username
                )
            }
    }
}