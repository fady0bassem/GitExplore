package com.fadybassem.gitexplore.app

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.fadybassem.gitexplore.utils.SingleLiveEvent
import com.fadybassem.gitexplore.utils.Status
import com.fadybassem.gitexplore.utils.disableTouch
import com.fadybassem.gitexplore.utils.enableTouch
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    var hasInitializedRootView = false
    private var rootView: View? = null

    var disableTouch = SingleLiveEvent<Status>()

    @Inject
    lateinit var baseApplication: BaseApplication

    private var shouldCheckKeyboard = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseApplication.setCurrentActivity(activity = activity)

        view.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val heightDiff: Int = view.rootView.height - (rect.bottom - rect.top)
            if (heightDiff > 500) { // if more than 100 pixels, its probably a keyboard
                shouldCheckKeyboard = true
            } else {
                if (shouldCheckKeyboard) {
                    shouldCheckKeyboard = false
                    clearFocusRecursive(rootView)
                }
            }
        }

    }

    fun getPersistentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
        layout: Int,
    ): View? {
        if (rootView == null) {
            // Inflate the layout for this fragment
            rootView = inflater?.inflate(layout, container, false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }

        setDisableTouch()
        return rootView
    }

    fun getPersistentView(composeView: () -> ComposeView): View? {
//        if (rootView == null)
//            return composeView()
//        else
//            (rootView?.parent as? ViewGroup)?.removeView(rootView)

        rootView = composeView()
        (rootView?.parent as? ViewGroup)?.removeView(rootView)
        setDisableTouch()
        return rootView
    }

    fun setDisableTouch() {
        disableTouch.observe(this) {
            if (it == Status.LOADING) {
                disableTouch()
            } else {
                enableTouch()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Hide the keyboard when the fragment is paused
        hideKeyboard()
    }

    override fun onResume() {
        super.onResume()
        // Clear focus when the fragment is resumed
        clearFocusRecursive(rootView)
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun clearFocusRecursive(view: View?) {
        if (view is ComposeView) {
            view.clearFocus()
        } else if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                clearFocusRecursive(view.getChildAt(i))
            }
        }
    }

    private fun checkAndClearFocus(rootView: View?) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val isKeyboardOpen = imm.isAcceptingText

        if (isKeyboardOpen) {
            clearFocusRecursive(rootView)
        }
    }
}