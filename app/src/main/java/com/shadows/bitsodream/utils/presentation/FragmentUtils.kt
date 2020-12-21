package com.shadows.bitsodream.utils.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

//method to call the bind the view using the utils delegate
fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)