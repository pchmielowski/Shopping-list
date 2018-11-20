package net.chmielowski.shoppinglist

interface BlockingAction<P> {
    suspend operator fun invoke(params: P)
}
