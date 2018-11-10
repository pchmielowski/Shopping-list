package net.chmielowski.shoppinglist

import io.reactivex.Completable

interface CompletableAction<P> : Action<P, Completable>
