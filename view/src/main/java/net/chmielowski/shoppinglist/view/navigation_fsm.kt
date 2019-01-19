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

/*
 * States
 */
sealed class State(@IdRes val destination: Int) {

    abstract fun onEvent(event: Event): State

    fun ignore(event: Event) = this

    fun reportError(event: Event): State = error("Event $event can't be handled in $this state.")

    override fun toString() = className()
}


object Start : State(0) {

    override fun onEvent(event: Event) = when (event) {
        AppStarted -> ShopList
        is ShopClicked -> reportError(event)
        BackClicked -> reportError(event)
    }
}

object ShopList : State(R.id.shopList) {

    override fun onEvent(event: Event) = when (event) {
        AppStarted -> reportError(event)
        is ShopClicked -> ItemList(event.id)
        BackClicked -> Start
    }
}

data class ItemList(val id: ShopId) : State(R.id.itemList) {

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
