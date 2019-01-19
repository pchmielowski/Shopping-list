package net.chmielowski.shoppinglist.view

import net.chmielowski.shoppinglist.ShopId
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class NavigationFsmTest {

    private lateinit var fsm: Fsm

    @Before
    fun setUp() {
        fsm = Fsm().apply { onEvent(AppStarted) }
    }

    @Test
    fun `opens at shop list`() {
        assertState(ShopList)
    }

    @Test
    fun `closes app on back clicked`() {
        postEvent(BackClicked)

        assertState(Start)
    }

    @Test
    fun `shop clicked`() {
        val shop = ShopId(5)

        postEvent(ShopClicked(shop))

        assertState(ItemList(shop))
    }

    private fun postEvent(event: Event) {
        fsm.onEvent(event)
    }

    private fun assertState(state: State) = assertThat(fsm.state, `is`(state))
}
