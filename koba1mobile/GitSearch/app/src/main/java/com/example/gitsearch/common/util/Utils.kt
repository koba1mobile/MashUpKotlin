package com.example.gitsearch.common.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Utils {
    companion object{
        fun showKeyboard(context: Context?, view: View){
            val inputMethodManager: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}