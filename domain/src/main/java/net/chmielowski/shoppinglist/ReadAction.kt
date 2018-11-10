package net.chmielowski.shoppinglist

import io.reactivex.Single

interface ReadAction<P, T> {

    interface Params

    operator fun invoke(params: P): Single<T>
}
