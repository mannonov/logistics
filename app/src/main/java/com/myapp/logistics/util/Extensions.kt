package com.myapp.logistics.util

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*

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

inline fun <T : TabLayout> T.onTabSelected(crossinline func: T.(position: Int) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.position?.let { func(it) }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {
            tab?.position?.let { func(it) }
        }
    })
}

@SuppressLint("SimpleDateFormat")
fun String?.getDate(): String? {
    return try {
        val yourmilliseconds = this.toString().toLong()
        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm")
        val resultdate = Date(yourmilliseconds)
        return sdf.format(resultdate)
    } catch (e: Exception) {
        "Unknown date"
    }
}
