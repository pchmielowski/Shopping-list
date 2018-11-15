package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.chmielowski.shoppinglist.shop.ColorEntity
import net.chmielowski.shoppinglist.shop.ObserveShops
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopRepository
import net.chmielowski.shoppinglist.view.addshop.IconViewModel
import net.chmielowski.shoppinglist.view.shops.ShopListViewModel
import net.chmielowski.shoppinglist.view.shops.ShopViewModel
import org.junit.Rule
import org.junit.Test


class ShopListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `no shops visible`() {
        val repo = ShopRepository.Fake()
        val model = ShopListViewModel(ObserveShops(repo))

        repo.observe.onNext(emptyList())
        model.noShops shouldHaveValue true

        repo.observe.onNext(
            listOf(
                ShopEntity("Grocery", ColorEntity(1, 2), 3).apply { id = 0 },
                ShopEntity("Hardware", ColorEntity(4, 1), 2).apply { id = 1 }
            )
        )
        model.noShops shouldHaveValue false
        model.shops shouldHaveValue listOf(
            ShopViewModel(0, "Grocery", 1 to 2, IconViewModel.drawable(3)),
            ShopViewModel(1, "Hardware", 4 to 1, IconViewModel.drawable(2))
        )
    }
}