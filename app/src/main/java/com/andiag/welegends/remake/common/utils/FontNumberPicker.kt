package com.andiag.welegends.remake.common.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import com.andiag.welegends.remake.R
import com.andiag.welegends.remake.common.base.ActivityBase


/**
 * Created by andyq on 25/12/2016.
 */
class FontNumberPicker(context: Context, attrs: AttributeSet) : NumberPicker(context, attrs) {

    //    var type: Typeface? = null

    init {
        descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
    }

    override fun addView(child: View) {
        super.addView(child)
        updateView(child)
    }

    override fun addView(child: View, index: Int,
                         params: android.view.ViewGroup.LayoutParams) {
        super.addView(child, index, params)
//        type = Typeface.createFromAsset(context.assets,
//                "fonts/Beaufort.otf")
        updateView(child)
    }

    override fun addView(child: View, params: android.view.ViewGroup.LayoutParams) {
        super.addView(child, params)

//        type = Typeface.createFromAsset(context.assets,
//                "fonts/Beaufort.otf")
        updateView(child)
    }

    private fun updateView(view: View) {

        if (view is EditText) {
            //view.typeface = type
            view.textSize = 25f
            view.setTextColor(ActivityBase.resolveColorAttribute(context, R.attr.mainColor))
        }

    }

}