package com.shudss00.gigachat.presentation.streamlist

import android.os.Bundle
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.databinding.FragmentStreamListBinding
import com.shudss00.gigachat.presentation.AppActivity
import com.shudss00.gigachat.presentation.base.MvpFragment
import com.shudss00.gigachat.presentation.extensions.doOnApplyWindowInsets
import com.shudss00.gigachat.presentation.streamlist.list.StreamListAdapter
import com.shudss00.gigachat.presentation.streamlist.list.StreamViewHolder
import com.shudss00.gigachat.presentation.streamlist.list.TopicViewHolder
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamListItem
import javax.inject.Inject

class StreamListFragment : MvpFragment<StreamListView, StreamListPresenter>(R.layout.fragment_stream_list),
    StreamListView {

    @Inject
    override lateinit var presenter: StreamListPresenter
    override var mvpView: StreamListView = this
    private lateinit var streamListAdapter: StreamListAdapter
    private val binding by viewBinding(FragmentStreamListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initUI() {
        setUpToolbar()
        setUpStreamListRecyclerView()
        setUpTabLayout()
        presenter.getStreams(null)
    }

    override fun showStreamList(list: List<StreamListItem>) {
        streamListAdapter.streamListItems = list
    }

    override fun showErrorToast(text: Int) {
        showToast(text)
    }

    private fun setUpTabLayout() {
        binding.tabLayoutStreams.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                when (tab.text) {
                    getString(R.string.tabItem_subscribed) -> {
                        presenter.currentStreamsType = StreamsType.SUBSCRIBED
                        presenter.getStreams(null)
                    }
                    getString(R.string.tabItem_allStreams) -> {
                        presenter.currentStreamsType = StreamsType.ALL_STREAMS
                        presenter.getStreams(null)
                    }
                }
            }
            override fun onTabUnselected(tab: Tab) {}
            override fun onTabReselected(tab: Tab) {}
        })
    }

    private fun setUpStreamListRecyclerView() {
        streamListAdapter = StreamListAdapter(
            object : StreamViewHolder.StreamItemClickListener {
                override fun onStreamItemClick(streamTitle: String) {
                    presenter.getStreams(streamTitle)
                }
            },
            object : TopicViewHolder.TopicItemClickListener {
                override fun onTopicItemClick(streamTitle: String, topicTitle: String) {
                    (requireActivity() as AppActivity).openMessengerFragment(streamTitle, topicTitle)
                }
            }
        )
        binding.recyclerViewStreamList.apply {
            adapter = streamListAdapter
            layoutManager = LinearLayoutManager(requireActivity())
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