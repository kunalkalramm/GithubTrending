package com.example.customspannable

import android.content.Context
import android.text.SpannableStringBuilder

class SpannableStringHelper(private val context: Context, private val spannablePropertyMap: HashMap<String, SpannableStringProperties>) {

    private var mSpannableBuilder = SpannableStringBuilder()

    fun getSpannableStringBuilder(): SpannableStringBuilder {
        for(item in spannablePropertyMap) {
            val spannableString = CustomSpannableString(context, item.key)
            spannableString.setProperties(item.value)
            mSpannableBuilder.append(spannableString)
        }

        return mSpannableBuilder
    }
}