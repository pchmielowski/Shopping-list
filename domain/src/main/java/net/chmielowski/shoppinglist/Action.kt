package net.chmielowski.shoppinglist

interface Action<P, R> {
    operator fun invoke(params: P): R
}

