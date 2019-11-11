package com.example.customspannable

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat

class CustomSpannableString(
    private val context: Context,
    text: String): SpannableString(text) {

    private val mStringLength = text.length

    fun setProperties(properties: SpannableStringProperties){

        properties.mFontSize?.let {
            val fontSizeInPx: Float = spTopx(it)
            val size = AbsoluteSizeSpan(fontSizeInPx.toInt(), false)
            super.setSpan(size, 0, mStringLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        properties.mFontTypeface?.let {
            val typefaceSpan = CustomTypefaceSpan(it)
            super.setSpan(typefaceSpan, 0, mStringLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        properties.mFontColorResId?.let {
            val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(context, it))
            super.setSpan(foregroundColorSpan, 0, mStringLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun spTopx(sp: Int) = sp * context.applicationContext.resources.displayMetrics.scaledDensity

}