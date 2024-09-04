package com.example.kbtech.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.kbtech.R
import com.example.kbtech.databinding.FragmentSomeMediaBinding

class SomeMediaFragment : Fragment() {

    lateinit var binding: FragmentSomeMediaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentSomeMediaBinding.inflate(layoutInflater, container, false)
        binding.progessBar.visibility = View.VISIBLE
        OpenWebView()

        return binding.root
    }

    private fun OpenWebView() {

        // Enable JavaScript (optional)
        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true

        // Load a URL
        binding.webView.loadUrl("https://kbtechmindtree.com/") // Replace with your URL
        binding.progessBar.visibility = View.GONE
        binding.webView.webViewClient = WebViewClient()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.webView.destroy()
        binding.progessBar.visibility = View.GONE
    }

    // Optional: Handle back navigation within the WebView
    fun canGoBack(): Boolean = binding.webView.canGoBack()
    fun goBack() {
        binding.webView.goBack()
    }
}