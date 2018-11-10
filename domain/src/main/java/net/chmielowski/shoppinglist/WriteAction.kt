package net.chmielowski.shoppinglist

import io.reactivex.Completable

@Deprecated("Use DataAction")
interface WriteAction<T> {
    operator fun invoke(t: T): Completable
}
