package com.shudss00.gigachat.presentation.userprofile

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.databinding.FragmentUserProfileBinding
import com.shudss00.gigachat.domain.model.User
import com.shudss00.gigachat.presentation.base.MvpFragment
import javax.inject.Inject

class UserProfileFragment : MvpFragment<UserProfileView, UserProfilePresenter>(R.layout.fragment_user_profile), UserProfileView {

    @Inject
    override lateinit var presenter: UserProfilePresenter
    override val mvpView: UserProfileView = this
    private val binding by viewBinding(FragmentUserProfileBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun initUI() {
        presenter.getOwnUser()
    }

    override fun showUser(user: User) {
        with(binding) {
            imageViewAvatar.load(user.avatar)
            textViewUsername.text = user.name
            textViewOnlineStatus.setOnlineStatus(user.onlineStatus)
        }
    }

    override fun showError(text: Int) {
        showToast(text)
    }
}