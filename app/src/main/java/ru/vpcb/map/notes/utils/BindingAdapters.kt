package ru.vpcb.map.notes.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("state")
fun backArrowState(view: View, state: Boolean = true) {
    view.visibility = if(state)View.VISIBLE else View.GONE
}

@BindingAdapter("icon")
fun imageResource(view: ImageView, resId: Int) {
    view.setImageResource(resId)
}