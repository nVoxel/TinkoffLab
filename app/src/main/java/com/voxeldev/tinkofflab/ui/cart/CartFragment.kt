package com.voxeldev.tinkofflab.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentCartBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens

class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        with(binding) {
            buttonMakeOrder.setOnClickListener { makeOrderButtonClicked() }
            return root
        }
    }

    private fun makeOrderButtonClicked() {
        App.router.navigateTo(Screens.DeliveryType())
    }
}
