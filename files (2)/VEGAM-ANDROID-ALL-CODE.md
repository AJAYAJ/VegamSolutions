# Vegam Digital Academy — Android Studio project (anni files oke chota)

Ee document lo Android app ki kavalsina **prathi file** undi.
Rendu vidhaluga vaadochu:

**A) Sulabham:** `vegam-android-studio.zip` extract chesi Android Studio lo **Open** kottandi. Files anni already unnayi — type cheyyalsina pani ledu.

**B) Manual:** Android Studio lo **New Project → Empty Views Activity** create chesi
(Name: `Vegam Digital Academy`, Package: `in.vegamdigital.app`, Language: **Kotlin**, Minimum SDK: **API 23**),
taruvatha kinda unna prathi file ni ade path lo create chesi, content paste cheyyandi.

---

## Marchalsindi okate line

`MainActivity.kt` file lo:

```kotlin
private const val START_URL = "https://vegamdigital.in/app"
```

Mee website link ikkada pettandi. Antey — migatha em marchakkarledu.

---

## APK ela generate cheyyali

1. Android Studio → **Build** → **Generate Signed App Bundle / APK**
2. **APK** select cheyyandi → Next
3. **Create new** keystore:
   - Path: safe chota save cheyyandi (Google Drive lo backup pettandi)
   - Password, alias, validity 25 years
   - **Ee keystore file poyithe app ki eppatiki update ivvaleru** — jagratha
4. Build variant: **release** → Create
5. APK ikkada vastundi: `app/release/app-release.apk`

Aa file ni students ki WhatsApp lo pampochu. Install chesetappudu "Unknown sources" allow cheyyamani adugutundi — Allow kottamani cheppandi.

Play Store kavalante Step 2 lo **Android App Bundle** select cheyyandi, `.aab` file vastundi.

---

## Icons

Zip lo `mipmap-*` folders lo icons already unnayi (5 sizes + round + adaptive).
Manual ga chestunte, Android Studio lo **res** folder meeda right click → **New → Image Asset** tho meere generate cheskovachu.

---

## Ee app enti chestundo

- Mee website ni app la open chestundi — login, videos, doubts, jobs anni pani chestayi
- **WhatsApp, phone links** native apps lo open avutayi (app lopala kaadu)
- **Class videos fullscreen** lo chudochu
- **Back button** app lopala back veltundi
- **Pull to refresh** undi
- **Internet leninappudu** proper message chupistundi
- **Login session save avutundi** — prathi sari login cheyyakkarledu

## Peddha advantage

App lopala unnadi mee website ye. Ante **Hostinger lo files update chesthe app automatic ga update avutundi** — kotta APK ivvakkarledu, students malli install cheyyakkarledu.

Kotta APK avasaram anedi app peru, icon, leda START_URL marchinappudu matrame.

---

# Anni files


## `settings.gradle.kts`

```kotlin
pluginManagement {
    repositories { google(); mavenCentral(); gradlePluginPortal() }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories { google(); mavenCentral() }
}
rootProject.name = "Vegam Digital Academy"
include(":app")
```

## `build.gradle.kts`

```kotlin
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
}
```

## `gradle.properties`

```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
```

## `gradle/wrapper/gradle-wrapper.properties`

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.7-bin.zip
networkTimeout=10000
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

## `app/build.gradle.kts`

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "in.vegamdigital.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "in.vegamdigital.app"
        minSdk = 23                 // Android 6.0 and above
        targetSdk = 34
        versionCode = 1             // update ki prathi sari penchandi
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { viewBinding = true }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-ktx:1.9.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.google.android.material:material:1.12.0")
}
```

## `app/proguard-rules.pro`

```text
-keepclassmembers class * { @android.webkit.JavascriptInterface <methods>; }
```

## `app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <!-- WhatsApp / phone / email links open cheyyadaniki -->
    <queries>
        <intent><action android:name="android.intent.action.VIEW" />
            <data android:scheme="https" /></intent>
        <intent><action android:name="android.intent.action.DIAL" />
            <data android:scheme="tel" /></intent>
        <intent><action android:name="android.intent.action.SENDTO" />
            <data android:scheme="mailto" /></intent>
    </queries>

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:usesCleartextTraffic="false"
        android:hardwareAccelerated="true"
        android:theme="@style/Theme.Vegam">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:configChanges="orientation|screenSize|keyboardHidden|smallestScreenSize|screenLayout"
            android:windowSoftInputMode="adjustResize"
            android:theme="@style/Theme.Vegam">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

## `app/src/main/java/in/vegamdigital/app/MainActivity.kt`

```kotlin
package `in`.vegamdigital.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
                    startActivity(Intent(Intent.ACTION_VIEW, url).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
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
```

## `app/src/main/res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/root"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/paper"
    android:fitsSystemWindows="true">

    <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
        android:id="@+id/refresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <WebView
            android:id="@+id/web"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>

    <ProgressBar
        android:id="@+id/progress"
        style="?android:attr/progressBarStyleHorizontal"
        android:layout_width="match_parent"
        android:layout_height="3dp"
        android:layout_gravity="top"
        android:max="100"
        android:progressTint="@color/brand"
        android:progressBackgroundTint="@color/paper" />

    <!-- internet leninappudu -->
    <LinearLayout
        android:id="@+id/offline"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:gravity="center"
        android:padding="32dp"
        android:background="@color/paper"
        android:visibility="gone">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="VEGAM"
            android:textColor="@color/ink"
            android:textSize="26sp"
            android:textStyle="bold"
            android:letterSpacing="0.08" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="14dp"
            android:gravity="center"
            android:text="@string/no_internet"
            android:textColor="@color/muted"
            android:textSize="15sp" />

        <Button
            android:id="@+id/retry"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:paddingHorizontal="24dp"
            android:text="@string/retry"
            android:textColor="@color/white"
            android:backgroundTint="@color/brand" />
    </LinearLayout>
</FrameLayout>
```

## `app/src/main/res/values/strings.xml`

```xml
<resources>
    <string name="app_name">Vegam Digital</string>
    <string name="no_internet">Internet ledu. Connection check chesi malli try cheyyandi.</string>
    <string name="retry">Malli try cheyyandi</string>
</resources>
```

## `app/src/main/res/values/colors.xml`

```xml
<resources>
    <color name="ink">#FF0B1230</color>
    <color name="paper">#FFEDF0F8</color>
    <color name="brand">#FF2B4BFF</color>
    <color name="gold">#FFFFC24B</color>
    <color name="white">#FFFFFFFF</color>
    <color name="muted">#FF6A7595</color>
    <color name="ic_launcher_background">#FF0B1230</color>
</resources>
```

## `app/src/main/res/values/themes.xml`

```xml
<resources>
    <style name="Theme.Vegam" parent="Theme.Material3.DayNight.NoActionBar">
        <item name="android:statusBarColor">@color/ink</item>
        <item name="android:navigationBarColor">@color/white</item>
        <item name="android:windowLightStatusBar">false</item>
        <item name="android:windowBackground">@color/paper</item>
    </style>
</resources>
```

## `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background" />
    <foreground android:drawable="@mipmap/ic_launcher_foreground" />
</adaptive-icon>
```

## `app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background" />
    <foreground android:drawable="@mipmap/ic_launcher_foreground" />
</adaptive-icon>
```

## `app/src/main/res/xml/backup_rules.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<full-backup-content />
```

---

# Binary files (type cheyyaleru)

Ivi images — zip lo already unnayi, leda Android Studio **Image Asset** tool tho generate cheskondi:

```
app/src/main/res/mipmap-mdpi/ic_launcher.png          (48x48)
app/src/main/res/mipmap-hdpi/ic_launcher.png          (72x72)
app/src/main/res/mipmap-xhdpi/ic_launcher.png         (96x96)
app/src/main/res/mipmap-xxhdpi/ic_launcher.png        (144x144)
app/src/main/res/mipmap-xxxhdpi/ic_launcher.png       (192x192)
... prathi folder lo ic_launcher_round.png and ic_launcher_foreground.png kuda
```

---

# Common problems

| Problem | Fix |
|---|---|
| "Gradle sync failed" | Internet on undali — modati sari Gradle download avutundi (~5 nimushalu) |
| White screen | START_URL tappu, leda site inka live kaledu. Browser lo aa link open chesi check cheyyandi |
| WhatsApp button pani cheyyaledu | Phone lo WhatsApp install undala chudandi |
| Video play avvatledu | YouTube video "Unlisted" ga undali, "Private" kaadu |
| Login save avvatledu | `domStorageEnabled = true` undala chudandi (already pettanu) |
| Build error: JDK | File → Settings → Build Tools → Gradle → Gradle JDK = **17** |
