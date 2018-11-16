package net.chmielowski.shoppinglist

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActionModule::class, PersistenceModule::class, TestDatabaseModule::class])
interface TestAppComponent : AppComponent {
    fun plusTestViewComponent(): TestViewComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplicationContext(context: Context): Builder

        fun build(): TestAppComponent
    }
}
