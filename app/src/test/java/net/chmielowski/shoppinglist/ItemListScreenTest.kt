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

    private val shop = 0L

    @Before
    fun setUp() {
        repo = ItemRepository.Fake()
        model = ItemsViewModel(ObserveItems(repo), SetCompleted(repo), shop)
    }

    @Test
    fun `marking item as completed`() {
        repo.observe.onNext(
            listOf(
                ItemEntity(0, "Bread", shop = shop),
                ItemEntity(1, "Butter", shop = shop)
            )
        )

        model.onToggled(0)
        repo.update.onNext(Unit)
        repo.observe.onNext(
            listOf(
                ItemEntity(1, "Butter", shop = shop)
            )
        )
        model.items shouldHaveValue listOf(ItemViewModel(1, "Butter", false))
    }
}
