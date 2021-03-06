package net.chmielowski.shoppinglist

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.StrictMode
import androidx.annotation.StringRes
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import kotlinx.coroutines.Dispatchers.IO
import net.chmielowski.shoppinglist.item.ItemRepository
import net.chmielowski.shoppinglist.item.ItemRepositoryImpl
import net.chmielowski.shoppinglist.shop.ShopColor
import net.chmielowski.shoppinglist.shop.ShopRepository
import net.chmielowski.shoppinglist.shop.ShopRepositoryImpl
import net.chmielowski.shoppinglist.view.MainActivity
import net.chmielowski.shoppinglist.view.Navigator
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.RealIconViewModelMapper
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel
import net.chmielowski.shoppinglist.view.addshop.IconViewModelMapper
import net.chmielowski.shoppinglist.view.addshop.IconsAdapter
import net.chmielowski.shoppinglist.view.items.AddItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemsAdapter
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
import net.chmielowski.shoppinglist.view.shops.*
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.android.viewmodel.ext.koin.viewModel
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
//        LeakCanary.install(this)

        Stetho.initializeWithDefaults(this)

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .build()
        )

        startKoin(this, listOf(persistenceModule, appModule))
    }

    protected open val appModule
        get() = module {
            single<MainActivity.Provider>()

            factory<Navigator>()

            factory<ShopsAdapter>()
            factory<IconsAdapter>()
            factory<ItemsAdapter>()

            factory { IO }

            factoryBy<ShopRepository, ShopRepositoryImpl>()
            factoryBy<ItemRepository, ItemRepositoryImpl>()

            viewModel { (shop: ShopId) -> AddItemViewModel(get(), shop) }

            factoryBy<IconMapper, IconMapperImpl>()
            factoryBy<IconViewModelMapper, RealIconViewModelMapper>()

            factoryBy<ColorMapper, ColorMapperImpl>()
            factory<Strings> { StringsImpl(androidContext()) }
            factory<ShopViewModelMapper>()
            viewModel<ShopListViewModel>()

            viewModel<AddShopViewModel>()
            viewModel { (shop: ShopId) -> ItemsViewModel(get(), get(), get(), shop) }
        }

}

class StringsImpl(private val context: Context) : Strings {

    override fun get(@StringRes resId: Int) = context.getString(resId)

    override fun format(@StringRes resId: Int, vararg formatArgs: Any) =
        context.getString(resId, *formatArgs)
}

class ColorMapperImpl : ColorMapper {

    override fun toInt(color: ShopColor) = Color.HSVToColor(
        floatArrayOf(
            color.first.toFloat() / 8.0f * 360.0f,
            color.second.toFloat(),
            1.0f
        )
    )
}

class IconMapperImpl : IconMapper {

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
            .associate { IconId(it.index) to it.value }
    }

    override fun toDrawableRes(id: IconId) = drawables[id]!!
}
