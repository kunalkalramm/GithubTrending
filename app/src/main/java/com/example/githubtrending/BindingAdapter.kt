package com.example.githubtrending

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(it)
            .into(imageView)
    }
}

@BindingAdapter("android:colorHex")
fun loadLanguageColor(imageView: ImageView, colorHex: String?) {
    colorHex?.let {
        imageView.background.mutate().colorFilter = BlendModeColorFilter(
            Color.parseColor(it),
            BlendMode.SRC
        )
    }

}