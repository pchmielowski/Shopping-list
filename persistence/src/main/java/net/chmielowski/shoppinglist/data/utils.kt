@file:JvmName("Utils")

package net.chmielowski.shoppinglist.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

@Deprecated("Use coroutines")
fun <T> Lazy<T>.asSingle() = Single.just(this)
    .subscribeOn(Schedulers.io())!!
