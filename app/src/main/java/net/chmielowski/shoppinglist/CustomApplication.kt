package net.chmielowski.shoppinglist

import android.app.Application
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

        ViewComponent.instance = DaggerAppComponent.builder()
            .bindApplicationContext(this)
            .build()
            .plusViewComponent()
    }
}
