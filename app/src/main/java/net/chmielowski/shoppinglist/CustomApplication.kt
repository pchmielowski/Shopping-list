package net.chmielowski.shoppinglist

import android.app.Application
import net.chmielowski.shoppinglist.view.ViewComponent

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ViewComponent.instance = DaggerAppComponent.builder()
            .bindApplicationContext(this)
            .build()
            .plusViewComponent()
    }
}
