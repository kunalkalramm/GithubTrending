//package com.example.githubtrending.utils.spannableUtils
//
//import android.graphics.Typeface
//
//class SpannableStringProperties(
//    var mFontSize: Int?,
//    var mFontTypeface: Typeface?,
//    var mFontColorResId: Int?) {
//
//    class Builder {
//        private var fontSize: Int? = null
//        private var fontTypeface: Typeface? = null
//        private var fontColorResId: Int? = null
//
//        fun setFontSize(fontSize: Int): Builder {
//            this.fontSize = fontSize
//            return this
//        }
//
//        fun setFonttypeface(typeface: Typeface): Builder {
//            this.fontTypeface = typeface
//            return this
//        }
//
//        fun setFontColorResId(resId: Int): Builder {
//            this.fontColorResId = resId
//            return this
//        }
//
//        fun build() = SpannableStringProperties(this.fontSize, this.fontTypeface, this.fontColorResId)
//    }
//}