package com.shudss00.gigachat.presentation.streamlist

import android.os.Bundle
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.databinding.FragmentStreamListBinding
import com.shudss00.gigachat.presentation.base.MvpFragment
import com.shudss00.gigachat.presentation.messenger.MessengerFragment
import com.shudss00.gigachat.presentation.streamlist.list.StreamListAdapter
import com.shudss00.gigachat.presentation.streamlist.list.StreamViewHolder
import com.shudss00.gigachat.presentation.streamlist.list.TopicViewHolder
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamListItem
import javax.inject.Inject

class StreamListFragment : MvpFragment<StreamListView, StreamListPresenter>(R.layout.fragment_stream_list),
    StreamListView, StreamViewHolder.StreamItemClickListener, TopicViewHolder.TopicItemClickListener {

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
        setUpStreamListRecyclerView()
        presenter.onCreate()
    }

    override fun showStreamList(list: List<StreamListItem>) {
        streamListAdapter.streamListItems = list
    }

    override fun showErrorToast(text: Int) {
        showToast(text)
    }

    override fun onStreamItemClick(streamTitle: String) {
        presenter.getAllStreams(streamTitle)
    }

    override fun onTopicItemClick(streamTitle: String, topicTitle: String) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragmentContainerView_mainContainer,
                MessengerFragment.newInstance(streamTitle, topicTitle)
            )
            addToBackStack(TAG)
        }
    }

    private fun setUpStreamListRecyclerView() {
        streamListAdapter = StreamListAdapter(this, this)
        binding.recyclerViewStreamList.apply {
            adapter = streamListAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    companion object {
        const val TAG = "STREAM_LIST_FRAGMENT"

        fun newInstance() = StreamListFragment()
    }
}