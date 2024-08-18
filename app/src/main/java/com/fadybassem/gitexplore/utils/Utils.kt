package com.fadybassem.gitexplore.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.fadybassem.gitexplore.BuildConfig
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.network.NetworkManager
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import kotlinx.coroutines.flow.FlowCollector
import java.util.regex.Pattern

/**
 * show toast message with context
 */
fun Context.showToastMessage(message: String?) {
    Toast.makeText(this, message ?: "\t", Toast.LENGTH_LONG).show()
}

/**
 * hide keyboard within the fragment
 */
fun Fragment.hideKeyboard() {
    val imm =
        this.requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var currentView = this.requireActivity().currentFocus
    if (currentView == null) {
        currentView = View(this.requireActivity())
    }
    imm.hideSoftInputFromWindow(currentView.windowToken, 0)
}

/**
 * hide keyboard within the activity
 */
fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var currentView = this.currentFocus
    if (currentView == null) {
        currentView = View(this)
    }
    imm.hideSoftInputFromWindow(currentView.windowToken, 0)
}

/**
 * disable touch within the activity
 */
fun Activity.disableTouch() {
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

/**
 * disable touch within the fragment
 */
fun Fragment.disableTouch() {
    this.requireActivity().window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

/**
 * enable touch within the activity
 */
fun Activity.enableTouch() {
    this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

/**
 * enable touch within the fragment
 */
fun Fragment.enableTouch() {
    this.requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

/**
 * hide system UI (fullscreen) within the activity
 */
fun Activity.hideSystemUI() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, this.window.decorView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

/**
 * show system UI within the activity
 */
fun Activity.showSystemUI() {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(
        window, this.window.decorView
    ).show(WindowInsetsCompat.Type.systemBars())

}

/**
 * get current version
 * */
fun appCurrentVersion(): String {
    return BuildConfig.VERSION_NAME
}

/**
 * check if email is valid
 */
fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * check if mobile is valid
 */
fun isPhoneNumberValid(phoneNumber: String): Boolean {
    return Pattern.matches("^[+]?[0-9]{9,15}$", phoneNumber)
}

/**
 * check for network availability
 */
suspend fun FlowCollector<Resource<User>>.checkForNetwork(
    networkManager: NetworkManager,
    resourceProvider: ResourceProvider,
): Boolean {
    if (!networkManager.isNetworkConnected()) {
        emit(Resource.Error(message = resourceProvider.getString(R.string.no_internet_connection)))
        return true
    }
    return false
}