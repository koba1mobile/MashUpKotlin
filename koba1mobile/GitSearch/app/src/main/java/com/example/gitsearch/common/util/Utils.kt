package com.example.gitsearch.common.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Utils {
    companion object{
        fun showKeyboard(context: Context?, view: View){
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
                showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}