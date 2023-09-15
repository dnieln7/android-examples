package com.dnieln7.portfoliomobile.utils.animation

import android.animation.ValueAnimator

class AnimationsStateHolder {

    private val animations: MutableMap<String, ValueAnimator> = mutableMapOf()

    fun add(tag: String, animator: ValueAnimator) {

        if (animations.contains(tag)) {
            animations[tag]?.removeAllListeners()
            animations[tag]?.removeAllUpdateListeners()
            animations.remove(tag)
        }

        addOrReplace(tag, animator)
    }

    fun reverse(tag: String) {
        animations[tag]?.reverse()
    }

    fun onDestroy() {
        animations.values.forEach {
            it.removeAllListeners()
            it.removeAllUpdateListeners()
            it.cancel()
        }
    }

    private fun addOrReplace(tag: String, animator: ValueAnimator) {
        animations[tag] = animator
    }
}
