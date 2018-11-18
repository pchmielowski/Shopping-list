package net.chmielowski.shoppinglist

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import net.chmielowski.shoppinglist.data.ActionModule
import net.chmielowski.shoppinglist.data.DatabaseModule
import net.chmielowski.shoppinglist.data.PersistenceModule
import net.chmielowski.shoppinglist.view.ViewComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [ActionModule::class, PersistenceModule::class, DatabaseModule::class])
interface AppComponent {
    fun plusViewComponent(): ViewComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplicationContext(context: Context): Builder

        fun build(): AppComponent
    }
}
