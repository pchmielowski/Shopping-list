package net.chmielowski.shoppinglist

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import net.chmielowski.shoppinglist.data.ActionModule
import net.chmielowski.shoppinglist.data.PersistenceModule
import net.chmielowski.shoppinglist.data.TestDatabaseModule
import net.chmielowski.shoppinglist.view.TestViewComponent
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
