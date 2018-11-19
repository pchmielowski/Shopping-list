package net.chmielowski.shoppinglist

import android.content.Context
import android.graphics.Color
import androidx.annotation.StringRes
import dagger.Module
import dagger.Provides
import dagger.Reusable
import net.chmielowski.shoppinglist.shop.ShopColor
import net.chmielowski.shoppinglist.view.IconMapper
import net.chmielowski.shoppinglist.view.shops.ColorMapper
import net.chmielowski.shoppinglist.view.shops.IconMapper2
import net.chmielowski.shoppinglist.view.shops.Strings

@Module
object ViewModule {
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
    fun provideIconMapper() = object : IconMapper2 {
        override fun toDrawableRes(id: Id) = IconMapper.drawableFromId(id)
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
