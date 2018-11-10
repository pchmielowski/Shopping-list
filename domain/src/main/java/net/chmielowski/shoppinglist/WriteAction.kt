package net.chmielowski.shoppinglist

import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject

interface WriteAction<T> {
    fun invoke(t: T): Completable
}

class FakeWriteAction<T> : WriteAction<T> {

    val subject = CompletableSubject.create()

    override fun invoke(t: T): Completable {
        return subject
    }
}
