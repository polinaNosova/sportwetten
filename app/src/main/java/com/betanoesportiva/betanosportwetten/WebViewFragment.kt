package com.betanoesportiva.betanosportwetten

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.betanoesportiva.betanosportwetten.databinding.FragmentWebViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        webView = binding.webView
        val bundle = this.arguments
        val url = bundle!!.getString(ARG_PARAM1)
        if (url != null) {
            initWebView(webView, url)
        }

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView(webView: WebView, url: String) {
        Log.i("url", url)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = MyWebViewClient()
        webView.loadUrl(url)

    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?,
        ): Boolean {
            if (request != null) {
                view?.loadUrl(request.url.toString())
            }
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    override fun onStart() {
        super.onStart()
        MainFragment().childReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ref = snapshot.value
                webView.loadUrl(ref.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
