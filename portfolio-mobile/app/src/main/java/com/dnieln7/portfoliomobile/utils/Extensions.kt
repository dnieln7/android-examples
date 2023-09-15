package com.dnieln7.portfoliomobile.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.dnieln7.portfoliomobile.R
import com.google.android.material.snackbar.Snackbar

fun View.snackLong(message: String, actionLabel: String? = null, action: () -> Unit = {}) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply {
        if (actionLabel != null) {
            setAction(actionLabel) { action() }
        }
    }.show()
}

fun Context.openBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    intent.addCategory(Intent.CATEGORY_BROWSABLE)
    startActivity(intent)
}

fun NavDirections.navigate(view: View, extras: Navigator.Extras = FragmentNavigatorExtras()) {
    view.findNavController().navigate(this, extras)
}

fun View.toPreviousScreen() {
    findNavController().popBackStack()
}

fun Activity.openEMail() {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:" + "dnieln7@gmail.com")

    startActivity(Intent.createChooser(intent, getString(R.string.send_email)))
}