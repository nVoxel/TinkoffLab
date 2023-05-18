package com.voxeldev.tinkofflab.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentBottomNavigationContainerBinding
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.cart.CartItemModel
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.utils.extensions.observe

class BottomNavigationFragment : BaseFragment<FragmentBottomNavigationContainerBinding>() {

    private val sharedOrderViewModel by activityViewModels<SharedOrderViewModel>()
    private val navigator by lazy {
        AppNavigator(requireActivity(), R.id.nested_container)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomNavigationContainerBinding.inflate(inflater, container, false)

        with(sharedOrderViewModel) {
            observe(cartItems, ::handleCartItems)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        binding?.bnvMain?.apply {
            selectedItemId = R.id.item_cart
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.item_catalog -> router.newRootScreen(Screens.Catalog())
                    R.id.item_cart -> router.newRootScreen(Screens.Cart())
                    R.id.item_orders -> router.newRootScreen(Screens.Orders())
                }
                arguments = null
                true
            }
            getOrCreateBadge(R.id.item_cart).apply {
                with(resources) {
                    val theme = requireContext().theme
                    badgeTextColor = getColor(R.color.badge_text, theme)
                    backgroundColor = getColor(R.color.badge_background, theme)
                    number = 0
                }
            }
        }
    }

    private fun handleCartItems(cartItems: List<CartItemModel>?) {
        val badge = binding?.bnvMain?.getOrCreateBadge(R.id.item_cart) ?: return
        cartItems?.let { list ->
            badge.number = list.filter { it.count > 0 }.sumOf { it.count }
        } ?: run { badge.number = 0 }
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

        binding?.bnvMain?.apply {
            selectedItemId = arguments?.getInt(START_SCREEN_KEY)?.takeIf { it != 0 }
                ?: selectedItemId
        }
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    companion object {

        private val cicerone = Cicerone.create()

        private val router = cicerone.router

        private val navigatorHolder get() = cicerone.getNavigatorHolder()

        private const val START_SCREEN_KEY = "START_SCREEN_KEY"

        fun getInstance(startScreenId: Int? = null) = BottomNavigationFragment().apply {
            startScreenId?.let {
                arguments = bundleOf(START_SCREEN_KEY to it)
            }
        }
    }
}
