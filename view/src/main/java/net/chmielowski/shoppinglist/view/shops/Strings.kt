package net.chmielowski.shoppinglist.view.shops

import androidx.annotation.StringRes

interface Strings {
    fun get(@StringRes resId: Int): String
    fun format(@StringRes resId: Int, vararg formatArgs: Any): String

    object Fake : Strings {
        override fun get(resId: Int) = ""
        override fun format(resId: Int, vararg formatArgs: Any) = ""
    }
}