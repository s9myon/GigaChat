package com.shudss00.gigachat.presentation.userlist

import android.os.Bundle
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.databinding.FragmentUserListBinding
import com.shudss00.gigachat.domain.model.User
import com.shudss00.gigachat.presentation.AppActivity
import com.shudss00.gigachat.presentation.base.MvpFragment
import com.shudss00.gigachat.presentation.extensions.doOnApplyWindowInsets
import com.shudss00.gigachat.presentation.extensions.hide
import com.shudss00.gigachat.presentation.extensions.show
import com.shudss00.gigachat.presentation.userlist.list.UserItemDecorator
import com.shudss00.gigachat.presentation.userlist.list.UserListAdapter
import javax.inject.Inject

class UserListFragment : MvpFragment<UserListView, UserListPresenter>(R.layout.fragment_user_list), UserListView {

    @Inject
    override lateinit var presenter: UserListPresenter
    override val mvpView: UserListView = this
    private lateinit var userListAdapter: UserListAdapter
    private val binding by viewBinding(FragmentUserListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun initUI() {
        setUpToolbar()
        setUpUserListRecyclerView()
        setUpSwipeRefreshLayoutListener()
        presenter.observeUsers()
    }

    override fun showAllUsers(list: List<User>) {
        userListAdapter.userItems = list
        with(binding) {
            recyclerViewUserList.show()
            progressBarUserList.root.hide()
        }
    }

    override fun showErrorToast(text: Int) {
        showToast(text)
    }

    override fun showLoading() {
        with(binding) {
            recyclerViewUserList.hide()
            progressBarUserList.root.show()
        }
    }

    private fun setUpUserListRecyclerView() {
        userListAdapter = UserListAdapter(
            object : UserListAdapter.UserClickListener {
                override fun onUserClick(userId: Long, username: String) {
                    (requireActivity() as AppActivity).openMessengerFragment(userId, username)
                }
            }
        )
        binding.recyclerViewUserList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(UserItemDecorator())
        }
    }

    private fun setUpToolbar() {
        with (binding) {
            toolbarUserList.doOnApplyWindowInsets { view, insets, initialPadding ->
                val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.updatePadding(
                    top = initialPadding.top + systemBarsInsets.top
                )
                WindowInsetsCompat.CONSUMED
            }
            // Note: toolbarUserList is custom SearchToolbar!!!
            toolbarUserList.doAfterTextChanged { message ->
                if (message.toString().isBlank()) {
                    presenter.observeUsers()
                } else {
                    presenter.searchUsers(message.toString())
                }
            }
            toolbarUserList.setOnSearchButtonClickListener {
                presenter.observeUsers()
            }
        }
    }

    private fun setUpSwipeRefreshLayoutListener() {
        with(binding) {
            swipeRefreshUserList.setOnRefreshListener {
                toolbarUserList.clearText()
                presenter.refreshUsers()
                swipeRefreshUserList.isRefreshing = false
            }
        }
    }
}