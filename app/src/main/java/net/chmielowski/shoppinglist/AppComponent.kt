package net.chmielowski.shoppinglist

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import net.chmielowski.shoppinglist.view.ViewComponent

@Component(modules = [PersistenceModule::class])
interface AppComponent {
    fun plusViewComponent(): ViewComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplicationContext(context: Context): Builder

        fun build(): AppComponent
    }
}
