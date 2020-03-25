package com.l.wikipediasearch.views

import android.os.Bundle
import android.view.MenuItem
import com.l.wikipediasearch.R

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            addFragment(WikiSearchFragment(), addToBackStack = false, transition = TRANSITION.NONE)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
