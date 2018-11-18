@file:JvmName("Utils")
package net.chmielowski.shoppinglist

import dagger.Lazy
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

fun <T> Lazy<T>.asSingle() = Single.fromCallable(this::get)
    .subscribeOn(Schedulers.io())!!
