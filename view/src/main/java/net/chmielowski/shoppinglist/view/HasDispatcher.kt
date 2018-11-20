package net.chmielowski.shoppinglist.view

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface HasDispatcher {
    val dispatcher: CoroutineDispatcher

    fun launch(block: suspend () -> Unit) {
        GlobalScope.launch(dispatcher) {
            block()
        }
    }
}