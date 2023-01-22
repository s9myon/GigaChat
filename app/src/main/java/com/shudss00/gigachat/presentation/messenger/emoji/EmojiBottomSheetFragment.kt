package com.shudss00.gigachat.presentation.messenger.emoji

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shudss00.gigachat.R
import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.databinding.FragmentEmojiBottomSheetBinding
import com.shudss00.gigachat.presentation.extensions.parcelable

class EmojiBottomSheetFragment: BottomSheetDialogFragment(R.layout.fragment_emoji_bottom_sheet),
    EmojiBottomSheetAdapter.OnEmojiClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_GigaChat_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEmojiBottomSheetBinding.inflate(inflater)
        with(binding.recyclerViewEmojiList) {
            layoutManager = GridLayoutManager(
                requireContext(),
                EMOJI_GRID_COLUMNS_NUMBER,
                RecyclerView.VERTICAL,
                false
            )
            adapter = EmojiBottomSheetAdapter(
                this@EmojiBottomSheetFragment
            )
        }
        return binding.root
    }

    override fun onEmojiClick(emoji: Emoji) {
        parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(
            KEY_MESSAGE_ID_RESPONSE to requireArguments().getLong(ARG_MESSAGE_ID),
            KEY_EMOJI_RESPONSE to emoji
        ))
        dismiss()
    }

    companion object {
        private const val EMOJI_GRID_COLUMNS_NUMBER = 7
        private const val ARG_MESSAGE_ID = "ARG_MESSAGE_ID"
        private const val KEY_MESSAGE_ID_RESPONSE = "KEY_MESSAGE_ID_RESPONSE"
        private const val KEY_EMOJI_RESPONSE = "KEY_EMOJI_RESPONSE"

        private val TAG = EmojiBottomSheetFragment::class.java.simpleName
        private val REQUEST_KEY = "$TAG:getEmojiRequestKey"

        fun show(manager: FragmentManager, messageId: Long) {
            val fragment = EmojiBottomSheetFragment()
            fragment.arguments = bundleOf(ARG_MESSAGE_ID to messageId)
            fragment.show(manager, TAG)
        }

        fun setUpResultListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (Long, Emoji) -> Unit
        ) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, bundle ->
                listener.invoke(
                    bundle.getLong(KEY_MESSAGE_ID_RESPONSE),
                    bundle.parcelable(KEY_EMOJI_RESPONSE)!!
                )
            }
        }
    }
}