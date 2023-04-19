package com.voxeldev.tinkofflab.ui

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {

        private val cicerone = Cicerone.create()

        val router = cicerone.router

        val navigatorHolder get() = cicerone.getNavigatorHolder()
    }
}
