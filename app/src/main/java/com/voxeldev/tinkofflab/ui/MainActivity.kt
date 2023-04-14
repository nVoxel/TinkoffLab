package com.voxeldev.tinkofflab.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.divider.MaterialDivider
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

    private val bottomNavigation: BottomNavigationView by lazy { findViewById(R.id.bnv_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.initAddressInputMode()
        setMenuItems()
        setBottomNavigation()
        if (savedInstanceState != null)
            return
        App.router.newRootScreen(Screens.Cart())
        bottomNavigation.selectedItemId = R.id.item_cart
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.navigatorHolder.removeNavigator()
    }

    private fun setBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_catalog -> {}
                R.id.item_cart -> App.router.newRootScreen(Screens.Cart())
                R.id.item_orders -> App.router.newRootScreen(Screens.Orders())
            }
            true
        }
    }

    fun showBottomNavigation() {
        window?.navigationBarColor = getColor(R.color.bottom_nav_background)
        bottomNavigation.isVisible = true
        findViewById<MaterialDivider>(R.id.divider).isVisible = true
    }

    fun hideBottomNavigation() {
        window?.navigationBarColor = getColor(R.color.md_theme_background)
        bottomNavigation.isVisible = false
        findViewById<MaterialDivider>(R.id.divider).isVisible = false
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
