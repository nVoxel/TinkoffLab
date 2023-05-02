package com.voxeldev.tinkofflab.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.voxeldev.tinkofflab.R
import kotlin.math.max

@SuppressLint("ResourceType")
class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val titleTextView: TextView
    private val startIcon: ImageButton
    val endIcon: ImageButton

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar_view_content, this, true)

        titleTextView = findViewById(R.id.toolbar_title)
        startIcon = findViewById(R.id.toolbar_icon_start)
        endIcon = findViewById(R.id.toolbar_icon_end)

        context.withStyledAttributes(attrs, R.styleable.CustomToolbar) {
            titleTextView.text = getString(R.styleable.CustomToolbar_title)

            getDrawable(R.styleable.CustomToolbar_startIcon)?.let {
                startIcon.setImageDrawable(it)
            } ?: run { startIcon.isVisible = false }

            getDrawable(R.styleable.CustomToolbar_endIcon)?.let {
                endIcon.setImageDrawable(it)
            } ?: run { endIcon.isVisible = false }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChild(titleTextView, widthMeasureSpec, heightMeasureSpec)
        measureChild(startIcon, widthMeasureSpec, heightMeasureSpec)
        measureChild(endIcon, widthMeasureSpec, heightMeasureSpec)

        val totalWidth = paddingLeft + paddingRight +
                titleTextView.measuredWidth + startIcon.measuredWidth + endIcon.measuredWidth

        val maxHeight = max(
            titleTextView.measuredHeight,
            max(startIcon.measuredHeight, endIcon.measuredHeight)
        )
        val totalHeight = paddingTop + paddingBottom + maxHeight

        setMeasuredDimension(
            resolveSize(totalWidth, widthMeasureSpec),
            resolveSize(totalHeight, heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // layout child views
        var left = paddingLeft
        val top = paddingTop
        var right = measuredWidth - paddingRight
        val bottom = measuredHeight - paddingBottom

        startIcon.layout(left, top, left + startIcon.measuredWidth, bottom)
        left += startIcon.measuredWidth

        endIcon.layout(right - endIcon.measuredWidth, top, right, bottom)
        right -= endIcon.measuredWidth

        val marginHorizontal = resources.getDimensionPixelSize(R.dimen.custom_toolbar_textview_margin_horizontal)
        left += marginHorizontal
        right -= marginHorizontal
        titleTextView.layout(left, top, right, bottom)
    }

    fun setTitle(title: String?) {
        titleTextView.text = title
    }

    fun setStartIconClickListener(listener: OnClickListener?) {
        startIcon.setOnClickListener(listener)
    }

    fun setEndIconClickListener(listener: OnClickListener?) {
        endIcon.setOnClickListener(listener)
    }
}
