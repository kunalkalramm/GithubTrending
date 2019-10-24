package com.example.githubtrending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Placeholder

class MainActivity : AppCompatActivity() {

    private lateinit var layout: ConstraintLayout
    private lateinit var placeholder: Placeholder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.layout)
        placeholder = findViewById(R.id.placeholder)
    }

    fun swapView(v: View) {
        TransitionManager.beginDelayedTransition(layout)
        placeholder.setContentId(v.id)
    }
}
