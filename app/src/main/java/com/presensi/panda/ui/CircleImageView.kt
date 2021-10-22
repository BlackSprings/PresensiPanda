package com.presensi.panda.ui

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import com.presensi.panda.R

class CircleImageView : AppCompatImageView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = ViewOutlineProvider.BACKGROUND
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            clipToOutline = true
        }
        setBackgroundResource(R.drawable.circle)
        scaleType = ScaleType.CENTER_CROP
    }
}