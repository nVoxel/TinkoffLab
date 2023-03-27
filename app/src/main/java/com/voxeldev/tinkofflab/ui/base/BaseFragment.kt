package com.voxeldev.tinkofflab.ui.base

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

open class BaseFragment<T : ViewBinding> : Fragment() {

    protected var binding: T? = null

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun showSnackbar(message: String) {
        Snackbar.make(binding?.root!!, message, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackbar(@StringRes message: Int) {
        Snackbar.make(binding?.root!!, message, Snackbar.LENGTH_LONG).show()
    }
}
