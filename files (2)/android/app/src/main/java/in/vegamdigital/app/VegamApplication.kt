package `in`.vegamdigital.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import `in`.vegamdigital.app.notifications.NotificationChannels

@HiltAndroidApp
class VegamApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
    }
}
