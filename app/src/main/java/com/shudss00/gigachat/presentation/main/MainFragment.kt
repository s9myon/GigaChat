package com.shudss00.gigachat.presentation.main

import android.os.Bundle
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shudss00.gigachat.R
import com.shudss00.gigachat.app.App
import com.shudss00.gigachat.databinding.FragmentMainBinding
import com.shudss00.gigachat.presentation.base.MvpFragment
import com.shudss00.gigachat.presentation.extensions.doOnApplyWindowInsets
import javax.inject.Inject

const val EXTRA_STACK_STATE = "EXTRA_STACK_STATE"

class MainFragment : MvpFragment<MainView, MainPresenter>(R.layout.fragment_main), MainView {

    @Inject
    override lateinit var presenter: MainPresenter
    override val mvpView: MainView = this
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        restoreTabStackState(savedInstanceState)
    }

    override fun initUI() {
        setUpBottomNavigationView()
        presenter.onCreate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(
            EXTRA_STACK_STATE,
            ArrayList(presenter.getNavigationItemStack())
        )
    }

    override fun selectBottomNavigationItem(itemId: Int) {
        binding.bottomNavViewMainMenu.selectedItemId = itemId
    }

    private fun restoreTabStackState(savedInstanceState: Bundle?) {
        savedInstanceState?.getStringArrayList(EXTRA_STACK_STATE)?.let { restoredStack ->
            presenter.restoreStack(restoredStack)
        }
    }

    private fun setUpBottomNavigationView() {
        with(binding) {
            bottomNavViewMainMenu.setOnItemSelectedListener { item ->
                presenter.onNavigationItemSelected(
                    childFragmentManager,
                    item.itemId,
                    R.id.fragmentMain_container)
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