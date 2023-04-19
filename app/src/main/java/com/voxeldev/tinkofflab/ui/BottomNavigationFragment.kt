package com.voxeldev.tinkofflab.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentBottomNavigationContainerBinding
import com.voxeldev.tinkofflab.ui.base.BaseFragment

class BottomNavigationFragment : BaseFragment<FragmentBottomNavigationContainerBinding>() {

    private val navigator by lazy {
        AppNavigator(requireActivity(), R.id.nested_container)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomNavigationContainerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomNavigation()
        if (savedInstanceState == null)
            binding?.bnvMain?.selectedItemId = R.id.item_cart
    }

    private fun setBottomNavigation() {
        binding?.bnvMain?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_catalog -> {}
                R.id.item_cart -> router.newRootScreen(Screens.Cart())
                R.id.item_orders -> router.newRootScreen(Screens.Orders())
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.window?.navigationBarColor =
            requireContext().getColor(R.color.bottom_nav_background)
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.navigationBarColor =
            requireContext().getColor(R.color.md_theme_background)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    companion object {

        private val cicerone = Cicerone.create()

        private val router = cicerone.router

        private val navigatorHolder get() = cicerone.getNavigatorHolder()
    }
}
