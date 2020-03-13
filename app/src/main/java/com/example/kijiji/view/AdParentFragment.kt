package com.example.kijiji.view

import android.widget.Toast
import androidx.fragment.app.Fragment

open class AdParentFragment : Fragment() {

    fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }
}