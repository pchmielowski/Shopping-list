package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.view.items.ItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ItemListTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var repo: ItemRepository.Fake
    private lateinit var model: ItemsViewModel

    @Before
    fun setUp() {
        repo = ItemRepository.Fake()
        model = ItemsViewModel(AddItem(repo), ReadItems(repo), SetCompleted(repo))
    }

    @Test
    fun `displays suggestions`() {
        repo.select.onNext(emptyList())

        model.onNewItemNameChange("B")
        repo.select.onNext(
            listOf(
                ItemEntity(0, "Bread", true),
                ItemEntity(1, "Butter", true)
            )
        )
        model.suggestions shouldContainItems listOf("Bread", "Butter")

        model.onNewItemNameChange("Br")
        repo.select.onNext(
            listOf(
                ItemEntity(0, "Bread", true)
            )
        )
        model.suggestions shouldContainItems listOf("Bread")
    }

    @Test
    fun `displays new added item`() {
        repo.select.onNext(emptyList())

        model.onNewItemNameChange("Bread")
        model.onQuantityChange("4")
        model.onAddingConfirmed()

        repo.insert.onNext(0)

        model.items shouldHaveValue listOf(
            ItemViewModel(
                0,
                "Bread",
                false,
                "4"
            )
        )

        model.onNewItemNameChange("Butter")
        model.onAddingConfirmed()

        repo.insert.onNext(1)

        model.items shouldHaveValue listOf(
            ItemViewModel(
                0,
                "Bread",
                false,
                "4"
            ),
            ItemViewModel(
                1,
                "Butter",
                false
            )
        )
    }

    @Test
    fun `displays new added item chosen from suggestion`() {
        repo.select.onNext(emptyList())

        model.onNewItemNameChange("B")
        repo.select.onNext(
            listOf(
                ItemEntity(0, "Bread", true, null),
                ItemEntity(1, "Butter", true, null)
            )
        )
        model.onSuggestionChosen(0)

        repo.update.onNext(Unit)

        model.items shouldContainItems listOf("Bread")
    }

    @Test
    fun `marking item as completed and not completed again`() {
        repo.select.onNext(listOf(ItemEntity(0, "Bread")))

        model.onToggled(0)
        repo.update.onNext(Unit)
        model.items shouldHaveValue listOf(ItemViewModel(0, "Bread", true, null))

        model.onToggled(0)
        repo.update.onNext(Unit)
        model.items shouldHaveValue listOf(ItemViewModel(0, "Bread", false, null))
    }
}
