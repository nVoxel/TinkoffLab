package com.voxeldev.tinkofflab.ui.utils

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.voxeldev.tinkofflab.R

fun View.slideFromTop() {
    if (!isVisible) {
        isVisible = true
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.sliding_from_top))
    }
}

fun View.slideToTop() {
    if (isVisible) {
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.sliding_to_top))
        isVisible = false
    }
}

@SuppressLint("ClickableViewAccessibility")
fun View.hideOnTouch() {
    setOnTouchListener { _, _ ->
        slideToTop()
        true
    }
}
