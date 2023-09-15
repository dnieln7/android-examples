package com.dnieln7.portfoliomobile.data.preferences

import android.content.Context
import android.content.SharedPreferences

class PortfolioMobilePreferences {

    private lateinit var preferences: SharedPreferences

    private val id: String = "PortfolioMobilePreferences"

    fun onCreate(context: Context) {
        preferences = context.getSharedPreferences(id, Context.MODE_PRIVATE)
    }

    fun setOverlayShown(tag: String) {
        preferences.edit().putBoolean(tag, true).apply()
    }

    fun isOverlayShown(tag: String): Boolean {
        return preferences.getBoolean(tag, false)
    }
}