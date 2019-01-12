package net.chmielowski.shoppinglist

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.StrictMode
import androidx.annotation.StringRes
import androidx.room.Room
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import net.chmielowski.shoppinglist.data.AppDatabase
import net.chmielowski.shoppinglist.data.item.AddItem
import net.chmielowski.shoppinglist.data.item.ItemDao
import net.chmielowski.shoppinglist.data.shop.AddShop
import net.chmielowski.shoppinglist.data.shop.ObserveShops
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.shop.ShopColor
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.RealIconViewModelMapper
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel
import net.chmielowski.shoppinglist.view.addshop.IconViewModelMapper
import net.chmielowski.shoppinglist.view.items.AddItemViewModel
import net.chmielowski.shoppinglist.view.shops.*
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

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

        factory<AppDatabase> {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "room-db")
                .fallbackToDestructiveMigration()
                .build()
        }

        factory<ItemDao> { get<AppDatabase>().itemDao }
        factory<ShopDao> { get<AppDatabase>().shopDao }

        factory<AddItemType> { AddItem(get<ItemDao>()) }
        viewModel { AddItemViewModel(get<AddItemType>()) }

        factory<AddShopType> { AddShop(get<ShopDao>()) }
        factory<IconMapper> { createIconMapper() }
        factory<IconViewModelMapper> { RealIconViewModelMapper(get<IconMapper>()) }
        viewModel { AddShopViewModel(get<AddShopType>(), get<IconViewModelMapper>()) }

        factory<ObserveShopsType> { ObserveShops(get<ShopDao>()) }
        factory<ColorMapper> { createColorMapper() }
        factory<Strings> { provideStrings(androidContext()) }
        factory { ShopViewModelMapper(get<Strings>(), get<ColorMapper>(), get<IconMapper>()) }
        viewModel { ShopListViewModel(get<ObserveShopsType>(), get<ShopViewModelMapper>()) }
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
