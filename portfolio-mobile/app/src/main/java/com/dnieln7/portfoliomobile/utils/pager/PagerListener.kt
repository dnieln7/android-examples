package com.dnieln7.portfoliomobile.utils.pager

import androidx.viewpager2.widget.ViewPager2

class PagerListener(private val onChanged: (Int) -> Unit) : ViewPager2.OnPageChangeCallback() {

    override fun onPageSelected(position: Int) {
        onChanged(position)
    }
}