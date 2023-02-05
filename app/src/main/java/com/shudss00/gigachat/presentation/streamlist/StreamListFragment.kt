package com.shudss00.gigachat.presentation.streamlist

import android.os.Bundle
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.databinding.FragmentStreamListBinding
import com.shudss00.gigachat.presentation.AppActivity
import com.shudss00.gigachat.presentation.base.MvpFragment
import com.shudss00.gigachat.presentation.extensions.doOnApplyWindowInsets
import com.shudss00.gigachat.presentation.streamlist.list.StreamViewHolder
import com.shudss00.gigachat.presentation.streamlist.list.TopicViewHolder
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamListItem
import javax.inject.Inject

class StreamListFragment : MvpFragment<StreamListView, StreamListPresenter>(R.layout.fragment_stream_list),
    StreamListView {

    @Inject
    override lateinit var presenter: StreamListPresenter
    override var mvpView: StreamListView = this
    private val binding by viewBinding(FragmentStreamListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initUI() {
        setUpToolbar()
        setUpStreamListTypeViewPager()
        setUpTabLayout()
        presenter.getStreams(null)
    }

    override fun showStreamList(list: List<StreamListItem>) {
        with(binding) {
            ((viewPagerStreamList.getChildAt(0) as? RecyclerView)
                ?.findViewHolderForAdapterPosition(viewPagerStreamList.currentItem) as? StreamListTypeAdapter.ViewHolder)
                ?.updateList(list)
        }
    }

    override fun showErrorToast(text: Int) {
        showToast(text)
    }

    private fun setUpTabLayout() {
        with(binding) {
            TabLayoutMediator(tabLayoutStreams, viewPagerStreamList) { tab, position ->
                when (position) {
                    StreamListType.SUBSCRIBED.position ->
                        tab.text = getString(StreamListType.SUBSCRIBED.stringRes)
                    StreamListType.ALL_STREAMS.position ->
                        tab.text = getString(StreamListType.ALL_STREAMS.stringRes)
                }
            }.attach()
        }
    }

    private fun setUpStreamListTypeViewPager() {
        binding.viewPagerStreamList.apply {
            adapter = StreamListTypeAdapter(
                object : StreamViewHolder.StreamItemClickListener {
                    override fun onStreamItemClick(streamTitle: String) {
                        presenter.getStreams(streamTitle)
                    }
                },
                object : TopicViewHolder.TopicItemClickListener {
                    override fun onTopicItemClick(streamTitle: String, topicTitle: String) {
                        (requireActivity() as AppActivity).openMessengerFragment(
                            streamTitle = streamTitle,
                            topicTitle = topicTitle,
                            stackName = this@StreamListFragment::class.simpleName
                        )
                    }
                }
            )
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        StreamListType.SUBSCRIBED.position ->
                            presenter.currentStreamListType = StreamListType.SUBSCRIBED
                        StreamListType.ALL_STREAMS.position ->
                            presenter.currentStreamListType = StreamListType.ALL_STREAMS
                    }
                }
            })
        }
    }

    private fun setUpToolbar() {
        binding.appbarLayoutStreamList.doOnApplyWindowInsets { view, insets, initialPadding ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = initialPadding.top + systemBarsInsets.top
            )
            WindowInsetsCompat.CONSUMED
        }
    }
}