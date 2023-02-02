package com.shudss00.gigachat.presentation.userlist

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.databinding.FragmentUserListBinding
import com.shudss00.gigachat.domain.model.User
import com.shudss00.gigachat.presentation.base.MvpFragment
import com.shudss00.gigachat.presentation.userlist.list.UserItemDecorator
import com.shudss00.gigachat.presentation.userlist.list.UserListAdapter
import javax.inject.Inject

class UserListFragment : MvpFragment<UserListView, UserListPresenter>(R.layout.fragment_user_list), UserListView {

    @Inject
    override lateinit var presenter: UserListPresenter
    override val mvpView: UserListView = this
    lateinit var userListAdapter: UserListAdapter
    private val binding by viewBinding(FragmentUserListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun initUI() {
        setUpToolbar()
        setUpUserListRecyclerView()
        presenter.getAllUsers()
    }

    override fun showAllUsers(list: List<User>) {
        userListAdapter.userItems = list
    }

    override fun showErrorToast(text: Int) {
        showToast(text)
    }

    private fun setUpUserListRecyclerView() {
        userListAdapter = UserListAdapter()
        binding.recyclerViewStreamList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(UserItemDecorator())
        }
    }

    private fun setUpToolbar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbarUserList) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = insets.top
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    companion object {
        const val TAG = "USER_LIST_FRAGMENT"

        fun newInstance() = UserListFragment()
    }
}