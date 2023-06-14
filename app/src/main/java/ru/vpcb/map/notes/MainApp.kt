package ru.vpcb.map.notes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.vpcb.map.notes.utils.CryptoPrefs
import timber.log.Timber

@HiltAndroidApp
class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        CryptoPrefs.init(this)
    }
}