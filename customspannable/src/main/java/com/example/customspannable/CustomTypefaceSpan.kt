package com.example.customspannable

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomTypefaceSpan(private val typeface: Typeface?): MetricAffectingSpan() {

    override fun updateMeasureState(textPaint: TextPaint) {
        textPaint.typeface = typeface
    }

    override fun updateDrawState(tp: TextPaint) {
        tp.typeface = typeface
    }

}