package com.dnieln7.portfoliomobile.utils.animation

import android.animation.ValueAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.animation.addListener
import androidx.core.view.updateLayoutParams
import com.google.android.material.card.MaterialCardView

object CardAnimators {

    fun expand(card: MaterialCardView, toHeight: Int, onStart: () -> Unit, onEnd: () -> Unit) {
        ValueAnimator.ofInt(
            card.height,
            card.height.plus(toHeight)
        ).apply {
            duration = 750
            interpolator = DecelerateInterpolator()

            addListener(
                onStart = { onStart() },
                onEnd = {
                    card.updateLayoutParams {
                        height = FrameLayout.LayoutParams.WRAP_CONTENT
                    }

                    onEnd()
                }
            )

            addUpdateListener { card.updateLayoutParams { height = it.animatedValue as Int } }
        }.start()
    }

    fun shrink(card: MaterialCardView, shrinkBy: Int, onEnd: () -> Unit) {
        ValueAnimator.ofInt(
            card.height,
            card.height.minus(shrinkBy)
        ).apply {
            duration = 500
            interpolator = AccelerateInterpolator()

            addListener(
                onEnd = { onEnd() },
            )

            addUpdateListener { card.updateLayoutParams { height = it.animatedValue as Int } }
        }.start()
    }
}