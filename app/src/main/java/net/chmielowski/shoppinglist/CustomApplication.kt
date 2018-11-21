package net.chmielowski.shoppinglist

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import net.chmielowski.shoppinglist.view.ViewComponent

open class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        Stetho.initializeWithDefaults(this)

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .build()
        )

        ViewComponent.instance = DaggerAppComponent.builder()
            .bindApplicationContext(this)
            .build()
            .plusViewComponent()
    }
}
