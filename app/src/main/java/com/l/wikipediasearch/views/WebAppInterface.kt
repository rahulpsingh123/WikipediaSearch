package com.example.githubsearchrepo.fragment

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterface
/** Instantiate the interface and set the context  */ internal constructor(var mContext: Context) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String?) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }

}