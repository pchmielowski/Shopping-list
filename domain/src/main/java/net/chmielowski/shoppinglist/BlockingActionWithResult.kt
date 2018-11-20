package net.chmielowski.shoppinglist

interface BlockingActionWithResult<P, R> {
    suspend operator fun invoke(params: P): R
}