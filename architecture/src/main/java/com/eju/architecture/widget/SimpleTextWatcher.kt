package com.eju.architecture.widget

import android.text.Editable
import android.text.TextWatcher

abstract class SimpleTextWatcher:TextWatcher {
    final override fun afterTextChanged(s: Editable?) {
    }

    final override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
    final override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        onTextChanged(s.toString())
    }
    protected abstract fun onTextChanged(str:String)
}