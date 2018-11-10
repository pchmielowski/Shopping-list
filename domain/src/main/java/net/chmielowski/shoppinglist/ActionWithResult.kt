package net.chmielowski.shoppinglist

import io.reactivex.Single

interface ActionWithResult<P, T> : Action<P, Single<T>>
