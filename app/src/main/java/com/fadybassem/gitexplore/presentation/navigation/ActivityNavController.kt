package com.fadybassem.gitexplore.presentation.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.fadybassem.gitexplore.utils.getActivity

/**
 * this would be the navigation controller for navigating from activity to another
 * - with extra bundle or not
 * - with activity in stack or not
 */
fun AppCompatActivity.startSingleNavigation(
    destination: Class<*>,
    bundle: Bundle? = null,
    withOutAnimation: Boolean = false,
) {
    val intent = Intent(this, destination)
    bundle?.let {
        intent.putExtras(bundle)
        startActivity(intent)
    } ?: startActivity(intent)
    finish()
    if (withOutAnimation) this.overridePendingTransition(0, 0)
}

fun FragmentActivity.startSingleNavigation(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, destination)
    bundle?.let {
        intent.putExtras(bundle)
        startActivity(intent)
    } ?: startActivity(intent)
    finish()
}

fun ComponentActivity.startSingleNavigation(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, destination)
    bundle?.let {
        intent.putExtras(bundle)
        startActivity(intent)
    } ?: startActivity(intent)
    finish()
}

fun Activity.startSingleNavigation(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, destination)
    bundle?.let {
        intent.putExtras(bundle)
        startActivity(intent)
    } ?: startActivity(intent)
    finish()
}

fun Context.startBaseNavigation(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, destination)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    bundle?.let {
        intent.putExtras(bundle)
        startActivity(intent)
    } ?: startActivity(intent)
    this.getActivity()?.finish()
}


fun AppCompatActivity.startActivityNavigation(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, destination)
    bundle?.let {
        intent.putExtras(bundle)
        startActivity(intent)
    } ?: startActivity(intent)
}

fun FragmentActivity.startActivityNavigation(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, destination)
    bundle?.let {
        intent.putExtras(bundle)
        startActivity(intent)
    } ?: startActivity(intent)
}

fun ComponentActivity.startActivityNavigation(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, destination)
    bundle?.let {
        intent.putExtras(bundle)
        startActivity(intent)
    } ?: startActivity(intent)
}

fun Activity.startActivityNavigation(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, destination)
    bundle?.let {
        intent.putExtras(bundle)
        startActivity(intent)
    } ?: startActivity(intent)
}