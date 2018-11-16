package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.view.ViewComponent

class TestApplication : CustomApplication() {
    override fun onCreate() {
        super.onCreate()
        ViewComponent.instance = DaggerTestAppComponent.builder()
            .bindApplicationContext(this)
            .build()
            .plusTestViewComponent()
    }
}
