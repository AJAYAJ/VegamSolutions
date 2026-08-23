package `in`.vegamdigital.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import `in`.vegamdigital.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        // ========================================================
        // MEE WEBSITE LINK — idi okate marchali
        // App direct ga student login page ki open avutundi.
        // Website home page kavalante: "https://vegamdigital.in"
        // ========================================================
        private const val START_URL = "https://vegamdigital.in/app"

        // Ee domains app lopala open avutayi. Migatha anni links
        // (WhatsApp, YouTube app, phone) bayata open avutayi.
        private val IN_APP_HOSTS = listOf("vegamdigital.in", "www.vegamdigital.in")
    }

    private lateinit var b: ActivityMainBinding
    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private var customView: View? = null
    private var customViewCallback: WebChromeClient.CustomViewCallback? = null

    private val fileChooser = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = if (result.resultCode == Activity.RESULT_OK) result.data else null
        filePathCallback?.onReceiveValue(
            WebChromeClient.FileChooserParams.parseResult(result.resultCode, data)
        )
        filePathCallback = null
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.web.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true                 // login session save avvadaniki
            databaseEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            mediaPlaybackRequiresUserGesture = false // class videos ki
            cacheMode = WebSettings.LOAD_DEFAULT
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(false)
            userAgentString = "$userAgentString VegamApp/1.0"
        }
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(b.web, true)

        b.web.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(v: WebView, req: WebResourceRequest): Boolean {
                val url = req.url
                val host = url.host ?: return false

                // mana site — app lopale open cheyyi
                if (IN_APP_HOSTS.any { host == it }) return false

                // WhatsApp, phone, mail, migatha links — native app lo open cheyyi
                return try {
                    startActivity(Intent(Intent.ACTION_VIEW, url).apply {                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
                    true
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this@MainActivity, "Ee link open cheyyaledu", Toast.LENGTH_SHORT).show()
                    true
                }
            }

            override fun onPageStarted(v: WebView, url: String?, favicon: Bitmap?) {
                b.progress.visibility = View.VISIBLE
            }

            override fun onPageFinished(v: WebView, url: String?) {
                b.progress.visibility = View.GONE
                b.refresh.isRefreshing = false
            }

            override fun onReceivedError(v: WebView, req: WebResourceRequest, err: WebResourceError) {
                if (req.isForMainFrame && !isOnline()) showOffline(true)
            }
        }

        // fullscreen video (YouTube) + file upload support
        b.web.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(v: WebView, p: Int) {
                b.progress.progress = p
            }

            override fun onShowCustomView(view: View, cb: CustomViewCallback) {
                if (customView != null) { cb.onCustomViewHidden(); return }
                customView = view
                customViewCallback = cb
                (window.decorView as ViewGroup).addView(
                    view, ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                b.root.visibility = View.GONE
            }

            override fun onHideCustomView() {
                customView?.let { (window.decorView as ViewGroup).removeView(it) }
                customView = null
                customViewCallback?.onCustomViewHidden()
                b.root.visibility = View.VISIBLE
            }

            override fun onShowFileChooser(
                v: WebView, cb: ValueCallback<Array<Uri>>, params: FileChooserParams
            ): Boolean {
                filePathCallback?.onReceiveValue(null)
                filePathCallback = cb
                return try { fileChooser.launch(params.createIntent()); true }
                catch (e: ActivityNotFoundException) { filePathCallback = null; false }
            }
        }

        // downloads (certificate, materials) browser ki pampandi
        b.web.setDownloadListener { url, _, _, _, _ ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        b.refresh.setOnRefreshListener { b.web.reload() }
        b.retry.setOnClickListener {
            if (isOnline()) { showOffline(false); b.web.loadUrl(START_URL) }
            else Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show()
        }

        // back button — app lopala back veltundi, chivarilo app close avutundi
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun handleOnBackPressed() {
                when {
                    customView != null -> b.web.webChromeClient?.onHideCustomView()
                    b.web.canGoBack() -> b.web.goBack()
                    else -> finish()
                }
            }
        })

        if (savedInstanceState == null) {
            if (isOnline()) b.web.loadUrl(START_URL) else showOffline(true)
        }
    }

    private fun showOffline(show: Boolean) {
        b.offline.visibility = if (show) View.VISIBLE else View.GONE
        b.refresh.visibility = if (show) View.GONE else View.VISIBLE
        b.progress.visibility = View.GONE
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(ConnectivityManager::class.java) ?: return false
        val net = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(net) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState); b.web.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState); b.web.restoreState(savedInstanceState)
    }

    override fun onPause() { super.onPause(); b.web.onPause() }
    override fun onResume() { super.onResume(); b.web.onResume() }
}
