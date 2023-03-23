package com.voxeldev.tinkofflab.ui.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import com.voxeldev.tinkofflab.R

private const val DEFAULT_SCALE = 0.95f

@SuppressLint("ClickableViewAccessibility")
fun CardView.setOnPressedAnim() {
    val downscale =
        AnimationUtils.loadAnimation(context, R.anim.button_downscale)
    val upscale =
        AnimationUtils.loadAnimation(context, R.anim.button_upscale)
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.startAnimation(downscale)
                v.scaleX = DEFAULT_SCALE
                v.scaleY = DEFAULT_SCALE

            }

            MotionEvent.ACTION_UP -> {
                v.startAnimation(upscale)
                v.scaleX = 1f
                v.scaleY = 1f
            }

            MotionEvent.ACTION_CANCEL -> {
                v.startAnimation(upscale)
                v.scaleX = 1f
                v.scaleY = 1f
            }
        }
        false
    }
}

