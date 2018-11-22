package net.chmielowski.shoppinglist.view

import android.content.Context
import android.graphics.Color
import androidx.annotation.StringRes
import androidx.navigation.findNavController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.ShopColor
import net.chmielowski.shoppinglist.view.addshop.IconViewModelMapper
import net.chmielowski.shoppinglist.view.shops.ColorMapper
import net.chmielowski.shoppinglist.view.shops.IconMapper
import net.chmielowski.shoppinglist.view.shops.Strings

@Module
abstract class ViewModule {
    @Binds
    @Reusable
    abstract fun bindIconViewModelMapper(impl: RealIconViewModelMapper): IconViewModelMapper

    @Module
    companion object {
        @Provides
        @Reusable
        @JvmStatic
        fun provideNavController(activity: MainActivity) =
            activity.findNavController(R.id.my_nav_host_fragment)


        @Provides
        @Reusable
        @JvmStatic
        fun provideStrings(context: Context) = object : Strings {
            override fun get(@StringRes resId: Int) = context.getString(resId)

            override fun format(@StringRes resId: Int, vararg formatArgs: Any) =
                context.getString(resId, *formatArgs)
        }

        @Provides
        @Reusable
        @JvmStatic
        fun provideIconMapper() = object : IconMapper {
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

        @Provides
        @Reusable
        @JvmStatic
        fun provideColorMapper() = object : ColorMapper {
            override fun toInt(color: ShopColor) = Color.HSVToColor(
                floatArrayOf(
                    color.first.toFloat() / 8.0f * 360.0f,
                    color.second.toFloat(),
                    1.0f
                )
            )
        }
    }
}
