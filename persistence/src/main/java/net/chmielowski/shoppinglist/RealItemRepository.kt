package net.chmielowski.shoppinglist

import io.reactivex.Completable
import io.reactivex.Single

fun <T> Single<T>.mapCompletable(mapper: (T) -> Unit) =
    flatMapCompletable { Completable.fromAction { mapper(it) } }!!

