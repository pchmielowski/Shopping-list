package net.chmielowski.shoppinglist.view

import net.chmielowski.shoppinglist.ShopId

private fun Any.className() = this::class.simpleName!!


/*
 * Events
 */
sealed class Event {

    override fun toString() = className()
}

object AppStarted : Event()

data class ShopClicked(val id: ShopId) : Event()

/*
 * States
 */
sealed class State {

    abstract fun onEvent(event: Event): State

    fun ignore(event: Event) = this

    fun reportError(event: Event): State = error("Event $event can't be handled in $this state.")

    override fun toString() = className()
}


private object Start : State() {

    override fun onEvent(event: Event) = when (event) {
        AppStarted -> ShopList
        is ShopClicked -> reportError(event)
    }
}

private object ShopList : State() {

    override fun onEvent(event: Event) = when (event) {
        AppStarted -> reportError(event)
        is ShopClicked -> Shop(event.id)
    }
}

private data class Shop(val id: ShopId) : State() {

    override fun onEvent(event: Event) = reportError(event)
}

/*
 * Fsm
 */
class Fsm {

    fun onEvent(event: Event) =
        state.onEvent(event)
            .also { new -> state = new }

    var state: State = Start
}
