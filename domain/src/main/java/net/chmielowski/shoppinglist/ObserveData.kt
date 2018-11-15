package net.chmielowski.shoppinglist

import io.reactivex.Observable

interface ObserveData<P, T> : Action<P, Observable<T>>
