package net.chmielowski.shoppinglist

import android.app.Application
import com.facebook.stetho.Stetho
import net.chmielowski.shoppinglist.view.ViewComponent

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        ViewComponent.instance = DaggerAppComponent.builder()
            .bindApplicationContext(this)
            .build()
            .plusViewComponent()
    }
}
