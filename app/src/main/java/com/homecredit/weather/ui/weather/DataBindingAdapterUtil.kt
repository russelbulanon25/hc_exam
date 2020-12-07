package com.homecredit.weather.ui.weather

import android.graphics.Color
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("derivedBackgroundColor")
fun ConstraintLayout.derivedBackgroundColor(hexColor: String) {
    this.setBackgroundColor(Color.parseColor(hexColor))
}

@BindingAdapter("showIf")
fun View.showIf(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}