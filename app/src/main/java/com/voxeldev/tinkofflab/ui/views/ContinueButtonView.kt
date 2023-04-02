package com.voxeldev.tinkofflab.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.voxeldev.tinkofflab.R

class ContinueButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val textView: TextView
    private val progressBar: ProgressBar

    // set true to show progress bar and false hide it
    var showLoading = false
        set(value) {
            textView.isVisible = !value
            progressBar.isVisible = value
            field = value
        }

    init {
        inflate(context, R.layout.continue_button_view_content, this)
        textView = findViewById(R.id.textview_button_title)
        progressBar = findViewById(R.id.progressbar_button)
        context.withStyledAttributes(attrs, R.styleable.ContinueButtonView) {
            textView.text = getString(R.styleable.ContinueButtonView_text)
            showLoading = getBoolean(R.styleable.ContinueButtonView_showLoading, false)
        }
        setUi()
    }

    private fun setUi() {
        setBackgroundResource(R.drawable.bg_button_continue)
        setPadding(resources.getDimensionPixelSize(R.dimen.continue_button_padding))
        isClickable = true
    }

    fun setText(text: String) {
        textView.text = text
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var measuredWidth = paddingLeft + paddingRight
        var measuredHeight = paddingTop + paddingBottom

        measureChild(textView, widthMeasureSpec, heightMeasureSpec)
        if (showLoading)
            measureChild(progressBar, widthMeasureSpec, heightMeasureSpec)

        measuredWidth += textView.measuredWidth
        measuredHeight += textView.measuredHeight

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (showLoading)
            progressBar.layoutCentered()
        else
            textView.layoutCentered()
    }

    private fun View.layoutCentered() {
        val left = (this@ContinueButtonView.width - measuredWidth) / 2
        val top = (this@ContinueButtonView.height - measuredHeight) / 2
        val right = left + measuredWidth
        val bottom = top + measuredHeight
        layout(left, top, right, bottom)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }
}
