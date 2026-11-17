package id.kridhawiyata.tokengenerator

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import android.app.Activity

class MainActivity : Activity() {

    private lateinit var webView: WebView
    private lateinit var progress: ProgressBar

    private val startUrl =
        "https://script.google.com/macros/s/AKfycbzT7fgLGO7DPG3JgEZNE1q1r97XYh_xZCMbJvIgzcrLVHVD1KzWcXsz4-H63rEITJ26TA/exec"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progress = findViewById(R.id.progressBar)

        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            loadsImagesAutomatically = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(false)
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = false
            displayZoomControls = false
            allowFileAccess = false
            allowContentAccess = true
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            webView.settings.safeBrowsingEnabled = true
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progress.progress = newProgress
                progress.visibility = if (newProgress >= 100) View.GONE else View.VISIBLE
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progress.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val uri = request?.url ?: return false
                val scheme = uri.scheme ?: return true

                if (scheme != "https" && scheme != "http") return true

                return if (isAllowedGoogleHost(uri.host)) {
                    false
                } else {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                    } catch (_: Exception) {
                        Toast.makeText(
                            this@MainActivity,
                            "Tautan tidak dapat dibuka.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    true
                }
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                if (request?.isForMainFrame == true) {
                    progress.visibility = View.GONE
                    Toast.makeText(
                        this@MainActivity,
                        "Halaman gagal dimuat. Periksa koneksi internet lalu muat ulang.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        if (savedInstanceState == null) {
            webView.loadUrl(startUrl)
        } else {
            webView.restoreState(savedInstanceState)
        }
    }

    private fun isAllowedGoogleHost(host: String?): Boolean {
        if (host.isNullOrBlank()) return false
        val h = host.lowercase()
        val allowedDomains = listOf(
            "script.google.com",
            "script.googleusercontent.com",
            "google.com",
            "googleusercontent.com",
            "gstatic.com"
        )
        return allowedDomains.any { h == it || h.endsWith(".$it") }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Deprecated in Android API; retained for back navigation compatibility")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        if (::webView.isInitialized) {
            webView.stopLoading()
            webView.destroy()
        }
        super.onDestroy()
    }
}
