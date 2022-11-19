package com.myapp.logistics.util

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.widget.Toolbar

inline fun <T : Toolbar> T.navOnClick(crossinline func: T.() -> Unit) =
    setNavigationOnClickListener { func() }

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }

fun <T : Animation> T.animationEndListener(func: T.() -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {}
        override fun onAnimationEnd(p0: Animation?) {
            func()
        }

        override fun onAnimationRepeat(p0: Animation?) {}
    })
}

fun <T : View> T.showWithAlphaAnimation() {
    startAnimation(
        AlphaAnimation(0.0f, 1.0f).apply {
            duration = 200
            startOffset = 0
            fillAfter = false
            animationEndListener { visibility = View.VISIBLE }
        }
    )
}

fun <T : View> T.hideWithAlphaAnimation() {
    startAnimation(
        AlphaAnimation(1.0f, 0.0f).apply {
            duration = 200
            startOffset = 0
            fillAfter = false
            animationEndListener { visibility = View.INVISIBLE }
        }
    )
}
