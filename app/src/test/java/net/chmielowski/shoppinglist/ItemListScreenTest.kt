package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.view.items.ItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ItemListScreenTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var repo: ItemRepository.Fake
    private lateinit var model: ItemsViewModel

    @Before
    fun setUp() {
        repo = ItemRepository.Fake()
        model = ItemsViewModel(ReadItems(repo), SetCompleted(repo))
    }

    @Test
    fun `marking item as completed and not completed again`() {
        repo.select.onNext(listOf(ItemEntity(0, "Bread")))

        model.onToggled(0)
        repo.update.onNext(Unit)
        model.items shouldHaveValue listOf(ItemViewModel(0, "Bread", true))

        model.onToggled(0)
        repo.update.onNext(Unit)
        model.items shouldHaveValue listOf(ItemViewModel(0, "Bread", false))
    }
}
