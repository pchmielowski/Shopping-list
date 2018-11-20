package net.chmielowski.shoppinglist

import io.reactivex.Single

@Deprecated("Use blocking action with result")
interface ActionWithResult<P, T> : Action<P, Single<T>>
