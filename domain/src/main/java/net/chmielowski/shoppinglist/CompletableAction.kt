package net.chmielowski.shoppinglist

import io.reactivex.Completable

@Deprecated("Use blocking action")
interface CompletableAction<P> : Action<P, Completable>
