package com.voxeldev.tinkofflab.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navigator = object : AppNavigator(this, R.id.container) {
        override fun setupFragmentTransaction(
            screen: FragmentScreen,
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment
        ) {
            // todo: can be removed later
            fragmentTransaction
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                )
        }
    }

    private val viewModel: SharedOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.initAddressInputMode()
        setMenuItems()
        if (savedInstanceState != null)
            return
        App.router.newRootScreen(Screens.HostFragment())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.navigatorHolder.removeNavigator()
    }

    private fun setMenuItems() {
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.address_toggle -> {
                        App.router.navigateTo(Screens.ToggleAddress())
                    }
                }
                return true
            }
        })
    }
}
