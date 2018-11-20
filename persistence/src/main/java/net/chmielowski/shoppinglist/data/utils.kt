@file:JvmName("Utils")

package net.chmielowski.shoppinglist.data

import dagger.Lazy
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

@Deprecated("Use coroutines")
fun <T> Lazy<T>.asSingle() = Single.fromCallable(this::get)
    .subscribeOn(Schedulers.io())!!
