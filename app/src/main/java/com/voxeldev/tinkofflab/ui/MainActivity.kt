package com.voxeldev.tinkofflab.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.voxeldev.tinkofflab.R

class MainActivity : AppCompatActivity() {

    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
            return
        App.router.newRootScreen(Screens.Cart())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.navigatorHolder.removeNavigator()
    }
}
