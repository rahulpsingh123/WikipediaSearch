package com.l.wikipediasearch.views

import android.annotation.SuppressLint
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.githubsearchrepo.fragment.WebAppInterface
import com.l.wikipediasearch.R
import kotlinx.android.synthetic.main.wiki_web_view_fragment.*

class WebViewFragment : Fragment() {
    private var title: String? = null
    private val DEFAULT_URL = "https://en.m.wikipedia.org/wiki/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wiki_web_view_fragment, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // progress on player resize
        val layoutParams = news_load_progress?.layoutParams as LinearLayout.LayoutParams
        news_load_progress?.measure(0, 0)
        layoutParams.setMargins(0, -(news_load_progress?.measuredHeight?.div(2))!!, 0, 0)
        news_load_progress?.layoutParams = layoutParams
        news_load_progress.show()

        (activity as AppCompatActivity?)?.setSupportActionBar(toolBar)
        val actionBar = (activity as AppCompatActivity?)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        toolBar.title = title

        web_view?.settings?.builtInZoomControls = true
        web_view?.settings?.displayZoomControls = false
        web_view?.settings?.javaScriptEnabled = true
        web_view?.addJavascriptInterface(WebAppInterface(activity!!), "android")
        web_view?.webViewClient = WebViewClient()
        val webUrl  = String.format("%s%s",DEFAULT_URL, title)

        web_view?.loadUrl(webUrl)
        web_view?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                news_load_progress?.hide()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String?): WebViewFragment {
            val webViewFragment = WebViewFragment()
            webViewFragment.title = url
            return webViewFragment
        }
    }
}