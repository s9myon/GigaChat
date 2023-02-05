package com.shudss00.gigachat.presentation.mainmenu

import android.os.Bundle
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.databinding.FragmentMainBinding
import com.shudss00.gigachat.presentation.base.MvpFragment
import com.shudss00.gigachat.presentation.extensions.doOnApplyWindowInsets
import javax.inject.Inject

const val TAB_STREAM_LIST = 0
const val TAB_USER_LIST = 1
const val TAB_USER_PROFILE = 2

class MainFragment : MvpFragment<MainMenuView, MainMenuPresenter>(R.layout.fragment_main), MainMenuView {

    @Inject
    override lateinit var presenter: MainMenuPresenter
    override val mvpView: MainMenuView = this
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initUI() {
        setUpViewPager()
        setUpBottomNavigationView()
    }

    private fun setUpViewPager() {
        with(binding) {
            viewPagerMainMenu.adapter = MainMenuAdapter(childFragmentManager, lifecycle)
            viewPagerMainMenu.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPagerMainMenu.setPageTransformer(ZoomOutPageTransformer())
            viewPagerMainMenu.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        TAB_STREAM_LIST ->
                            bottomNavViewMainMenu.menu.findItem(R.id.item_streamList).isChecked = true
                        TAB_USER_LIST ->
                            bottomNavViewMainMenu.menu.findItem(R.id.item_userList).isChecked = true
                        TAB_USER_PROFILE ->
                            bottomNavViewMainMenu.menu.findItem(R.id.item_profile).isChecked = true
                    }
                }
            })
        }
    }

    private fun setUpBottomNavigationView() {
        with(binding) {
            bottomNavViewMainMenu.setOnItemSelectedListener { tab ->
                when (tab.itemId) {
                    R.id.item_streamList -> {
                        viewPagerMainMenu.currentItem = TAB_STREAM_LIST
                        true
                    }
                    R.id.item_userList -> {
                        viewPagerMainMenu.currentItem = TAB_USER_LIST
                        true
                    }
                    R.id.item_profile -> {
                        viewPagerMainMenu.currentItem = TAB_USER_PROFILE
                        true
                    }
                    else -> false
                }
            }
            bottomNavViewMainMenu.doOnApplyWindowInsets { view, insets, initialPadding ->
                val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.updatePadding(
                    bottom = initialPadding.bottom + systemBarsInsets.bottom
                )
                WindowInsetsCompat.CONSUMED
            }
        }
    }
}