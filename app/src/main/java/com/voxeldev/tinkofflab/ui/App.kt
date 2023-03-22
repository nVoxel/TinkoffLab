package com.voxeldev.tinkofflab.ui

import android.app.Application
import com.github.terrakok.cicerone.Cicerone

class App : Application() {
    companion object {
        val cicerone = Cicerone.create()
        val router = cicerone.router
        val navigatorHolder get() = cicerone.getNavigatorHolder()
    }
}
