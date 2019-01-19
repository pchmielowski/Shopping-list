package net.chmielowski.shoppinglist.view

import androidx.annotation.IdRes
import net.chmielowski.shoppinglist.ShopId
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class NavigationFsmTest {

    private lateinit var fsm: Fsm

    private lateinit var navigator: FakeNavigator

    @Before
    fun setUp() {
        navigator = FakeNavigator()
        fsm = Fsm(navigator).apply { onEvent(AppStarted) }
    }

    @Test
    fun `opens at shop list`() {
        assertNavigatesToFragment(R.id.shopList)
    }

    @Test
    fun `closes app on back clicked`() {
        postEvent(BackClicked)

        assertGoesBack()
    }

    @Test
    fun `shop clicked`() {
        val shop = ShopId(5)

        postEvent(ShopClicked(shop))

        assertNavigatesToFragment(R.id.itemList)
    }

    @Test
    fun `opens shop list when back on item list clicked`() {
        postEvent(ShopClicked(ShopId(5)))
        postEvent(BackClicked)

        assertNavigatesToFragment(R.id.shopList)
    }

    @Test
    fun `opens shop list when shop is deleted`() {
        postEvent(ShopClicked(ShopId(5)))
        postEvent(ShopDeleted)

        assertNavigatesToFragment(R.id.shopList)
    }

    private fun assertNavigatesToFragment(@IdRes id: Int) =
        verifyNavigateTo(Destination.Fragment(id))

    private fun assertGoesBack() = verifyNavigateTo(Destination.Back)

    private fun verifyNavigateTo(destination: Destination) =
        assertThat(navigator.lastDestination, `is`(destination))

    private fun postEvent(event: Event) {
        fsm.onEvent(event)
    }

    class FakeNavigator : FsmNavigator {
        lateinit var lastDestination: Destination

        override fun navigateTo(destination: Destination) {
            lastDestination = destination
        }
    }
}

