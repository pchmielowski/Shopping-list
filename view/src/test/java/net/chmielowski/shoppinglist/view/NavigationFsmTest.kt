package net.chmielowski.shoppinglist.view

import net.chmielowski.shoppinglist.ShopId
import org.junit.Before
import org.junit.Test

class NavigationFsmTest {

    private lateinit var fsm: Fsm

    @Before
    fun setUp() {
        fsm = Fsm().apply { onEvent(AppStarted) }
    }

    @Test
    fun `shop clicked`() {
        val shop = ShopId(5)
        fsm.onEvent(ShopClicked(shop))
    }
}
