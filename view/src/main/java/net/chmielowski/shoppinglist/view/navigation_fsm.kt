package net.chmielowski.shoppinglist.view

import androidx.annotation.IdRes
import net.chmielowski.shoppinglist.ShopId

private fun Any.className() = this::class.simpleName!!


/*
 * Events
 */
sealed class Event {

    override fun toString() = className()
}

object AppStarted : Event()

object BackClicked : Event()

data class ShopClicked(val id: ShopId) : Event()

object ShopDeleted : Event()

/*
 * States
 */
sealed class State {

    abstract fun onEvent(event: Event): State

    fun reportError(event: Event): State = error("Event $event can't be handled in $this state.")

    override fun toString() = className()
}


private object Start : State() {

    override fun onEvent(event: Event) = when (event) {
        AppStarted -> ShopList
        is ShopClicked -> reportError(event)
        BackClicked -> reportError(event)
        ShopDeleted -> reportError(event)
    }
}

private object ShopList : State() {

    override fun onEvent(event: Event) = when (event) {
        AppStarted -> reportError(event)
        is ShopClicked -> ItemList(event.id)
        BackClicked -> Start
        ShopDeleted -> reportError(event)
    }
}

private data class ItemList(val id: ShopId) : State() {

    override fun onEvent(event: Event) = when (event) {
        AppStarted -> reportError(event)
        is ShopClicked -> reportError(event)
        BackClicked -> ShopList
        ShopDeleted -> ShopList
    }
}

/*
 * Fsm
 */
class Fsm(val navigator: FsmNavigator) {

    fun onEvent(event: Event) =
        state.onEvent(event)
            .also { new ->
                state = new
                navigator.navigateTo(destination(state))
            }

    private fun destination(state: State) = when (state) {
        Start -> Destination.Back
        ShopList -> Destination.Fragment(R.id.shopList)
        is ItemList -> Destination.Fragment(R.id.itemList)
    }

    var state: State = Start
}

sealed class Destination {

    object Back : Destination()

    data class Fragment(@IdRes val id: Int) : Destination()
}

interface FsmNavigator {

    fun navigateTo(destination: Destination)
}
