package com.voxeldev.tinkofflab.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.ui.toggleaddress.ToggleAddressFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navigator = AppNavigator(this, R.id.container)

    var addressAutofillToggle: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            addressAutofillToggle = savedInstanceState.getBoolean(TOGGLE_KEY, true)
        } else {
            startToggleFragmentForResult()
        }
    }

    private fun startToggleFragmentForResult() {
        supportFragmentManager.setFragmentResultListener(
            ToggleAddressFragment.TOGGLE_REQUEST_KEY,
            this
        ) { _, bundle ->
            addressAutofillToggle = bundle.getBoolean(ToggleAddressFragment.TOGGLE_KEY, true)
            App.router.newRootScreen(Screens.Cart())
        }
        App.router.newRootScreen(Screens.ToggleAddress())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        addressAutofillToggle?.let {
            outState.putBoolean(TOGGLE_KEY, it)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.navigatorHolder.removeNavigator()
    }

    companion object {

        private const val TOGGLE_KEY = "TOGGLE_KEY"
    }
}
