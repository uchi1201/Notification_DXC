package com.android.example.notification.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Fragment
 */
abstract class BaseFragment<T : ViewBinding>(val inflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> T) : Fragment() {

    protected lateinit var viewBinding: T
    protected lateinit var activity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireActivity()
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = this.inflater(inflater, container, false)
        return viewBinding.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
    }

}