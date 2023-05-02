package com.voxeldev.tinkofflab.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.ui.utils.hideOnTouch
import com.voxeldev.tinkofflab.ui.utils.slideFromTop
import com.voxeldev.tinkofflab.ui.utils.slideToTop
import com.voxeldev.tinkofflab.utils.platform.ConnectivityStateObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

    private val menuProvider = object : MenuProvider {
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
    }

    private val noConnectionView by lazy {
        findViewById<View>(R.id.no_connection).apply {
            hideOnTouch()
        }
    }

    @Inject
    lateinit var connectivityStateObserver: ConnectivityStateObserver

    private val viewModel: SharedOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.initAddressInputMode()
        showMenuItems()
        registerNetworkListener()
        if (savedInstanceState != null)
            return
        App.router.newRootScreen(Screens.HostFragment())
    }

    private fun registerNetworkListener() {
        connectivityStateObserver.registerNetworkCallback(
            onAvailable = { noConnectionView.slideToTop() },
            onLost = {
                noConnectionView.slideFromTop()
                noConnectionView.postDelayed({
                    noConnectionView.slideToTop()
                }, NO_CONNECTION_TIMEOUT)
            },
            handler = Handler(Looper.getMainLooper())
        )
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityStateObserver.unregisterNetworkCallback()
    }

    fun showMenuItems() {
        addMenuProvider(menuProvider)
    }

    fun hideMenuItems() {
        removeMenuProvider(menuProvider)
    }

    companion object {

        private const val NO_CONNECTION_TIMEOUT = 3000L
    }
}
