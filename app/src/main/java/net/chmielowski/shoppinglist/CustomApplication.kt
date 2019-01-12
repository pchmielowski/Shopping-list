package net.chmielowski.shoppinglist

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.StrictMode
import androidx.annotation.StringRes
import androidx.navigation.findNavController
import androidx.room.Room
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import net.chmielowski.shoppinglist.data.AppDatabase
import net.chmielowski.shoppinglist.data.item.AddItem
import net.chmielowski.shoppinglist.data.shop.AddShop
import net.chmielowski.shoppinglist.data.shop.ObserveShops
import net.chmielowski.shoppinglist.shop.ShopColor
import net.chmielowski.shoppinglist.view.MainActivity
import net.chmielowski.shoppinglist.view.Navigator
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.RealIconViewModelMapper
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel
import net.chmielowski.shoppinglist.view.addshop.IconViewModelMapper
import net.chmielowski.shoppinglist.view.addshop.IconsAdapter
import net.chmielowski.shoppinglist.view.items.AddItemViewModel
import net.chmielowski.shoppinglist.view.shops.*
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.factoryBy
import org.koin.experimental.builder.single

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

        startKoin(this, listOf(createModule()))
    }

    @Suppress("RemoveExplicitTypeArguments")
    private fun createModule() = module {

        single<MainActivity.Provider>()

        factory { get<MainActivity.Provider>().instance.findNavController(R.id.my_nav_host_fragment) }
        factory<Navigator>()

        factory<ShopsAdapter>()
        factory<IconsAdapter>()

        factory {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "room-db")
                .fallbackToDestructiveMigration()
                .build()
        }

        factory { get<AppDatabase>().itemDao }
        factory { get<AppDatabase>().shopDao }

        factory<CoroutineDispatcher> { IO }

        factoryBy<AddItemType, AddItem>()
        viewModel<AddItemViewModel>()

        factoryBy<AddShopType, AddShop>()
        factory<IconMapper> { createIconMapper() }
        factoryBy<IconViewModelMapper, RealIconViewModelMapper>()
        viewModel<AddShopViewModel>()

        factoryBy<ObserveShopsType, ObserveShops>()
        factory<ColorMapper> { createColorMapper() }
        factory<Strings> { provideStrings(androidContext()) }
        factory<ShopViewModelMapper>()
        viewModel<ShopListViewModel>()
    }


    private fun provideStrings(context: Context) = object : Strings {
        override fun get(@StringRes resId: Int) = context.getString(resId)

        override fun format(@StringRes resId: Int, vararg formatArgs: Any) =
            context.getString(resId, *formatArgs)
    }

    private fun createColorMapper(): ColorMapper {
        return object : ColorMapper {
            override fun toInt(color: ShopColor) = Color.HSVToColor(
                floatArrayOf(
                    color.first.toFloat() / 8.0f * 360.0f,
                    color.second.toFloat(),
                    1.0f
                )
            )
        }
    }

    private fun createIconMapper(): IconMapper {
        return object : IconMapper {
            private val drawables by lazy {
                arrayOf(
                    R.drawable.ic_shop_electronic,
                    R.drawable.ic_shop_grocery,
                    R.drawable.ic_shop_pharmacy,
                    R.drawable.ic_shop_sport,
                    R.drawable.ic_shop_stationers,
                    R.drawable.ic_shop_children,
                    R.drawable.ic_shop_business,
                    R.drawable.ic_shop_rtv
                ).withIndex()
                    .associate { it.index.toLong() to it.value }
            }

            override fun toDrawableRes(id: Id) = drawables[id]!!
        }
    }
}
