package com.example.githubtrending

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:imageUrl")
fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(it)
            .into(this)
    }
}

@BindingAdapter("android:colorHex")
fun ImageView.loadLanguageColor(colorHex: String?) {
    colorHex?.let {
        this.background.mutate().colorFilter = BlendModeColorFilter(
            Color.parseColor(it),
            BlendMode.SRC
        )
    }
}

@BindingAdapter("android:repoURL")
fun TextView.loadUrlWithCustomTab(url: String?) {
    this.setOnClickListener {
        url?.let {
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(this.context, Uri.parse(url))
        }
    }
}