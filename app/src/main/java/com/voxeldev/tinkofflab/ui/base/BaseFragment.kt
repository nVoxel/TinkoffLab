package com.voxeldev.tinkofflab.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.views.CustomToolbar


open class BaseFragment<T : ViewBinding> : Fragment() {

    protected var binding: T? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarOnClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setToolbarOnClickListener() {
        getCustomToolbar()?.run {
            setStartIconClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
        }
    }

    /**
     * Adds R.menu.toolbar_menu to the toolbar's end icon,
     * app:endIcon should be set in xml
     */
    fun addEndIconMenu() {
        getCustomToolbar()?.run {
            setEndIconClickListener {
                PopupMenu(requireContext(), this.endIcon, Gravity.END).apply {
                    menuInflater.inflate(R.menu.toolbar_menu, menu)

                    setOnMenuItemClickListener {
                        return@setOnMenuItemClickListener when (it.itemId) {
                            R.id.address_toggle -> {
                                App.router.navigateTo(Screens.ToggleAddress())
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }

    fun showSnackbar(message: String) {
        Snackbar.make(binding?.root!!, message, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackbar(@StringRes message: Int) {
        Snackbar.make(binding?.root!!, message, Snackbar.LENGTH_LONG).show()
    }

    fun showAlertDialog(@StringRes message: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.error_alert_dialog_title)
            .setMessage(message)
            .setPositiveButton(
                R.string.alert_dialog_ok
            ) { _, _ -> }
            .show()
    }

    private fun getCustomToolbar(): CustomToolbar? = binding?.root?.findViewById(R.id.toolbar)
}
