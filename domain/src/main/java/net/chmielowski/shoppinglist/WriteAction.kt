package net.chmielowski.shoppinglist

import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject

interface WriteAction<P : DataAction.Params> {
    operator fun invoke(t: P): Completable
}

class FakeWriteAction<P : DataAction.Params> : WriteAction<P> {

    val subject = PublishSubject.create<Unit>()

    override fun invoke(t: P) = subject.firstOrError().ignoreElement()!!
}
