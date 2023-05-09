package com.voxeldev.tinkofflab.ui.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.voxeldev.tinkofflab.R

class OrderCanceledDialog(context: Context) : Dialog(context, false, null) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_order_canceled)
        setCancelable(true)
        window?.run {
            setBackgroundDrawableResource(R.color.canceled_dialog_outer_background)
            addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }
}
