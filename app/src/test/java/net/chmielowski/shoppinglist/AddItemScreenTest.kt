package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.view.items.AddItemViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddItemScreenTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var repo: ItemRepository.Fake
    private lateinit var model: AddItemViewModel

    @Before
    fun setUp() {
        repo = ItemRepository.Fake()
        model = AddItemViewModel(AddItem(repo), ReadItems(repo), SetCompleted(repo))
    }

    @Test
    fun `displays suggestions`() {
        repo.select.onNext(
            listOf(
                ItemEntity(0, "Bread", true),
                ItemEntity(1, "Butter", true),
                ItemEntity(2, "Onion", true)
            )
        )

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

        model.onNewItemNameChange("Butter")
        model.onAddingConfirmed()

        repo.insert.onNext(1)
    }

    @Test
    fun `displays new added item chosen from suggestion`() {
        repo.select.onNext(emptyList())

        model.onNewItemNameChange("B")
        repo.select.onNext(
            listOf(
                ItemEntity(0, "Bread", true),
                ItemEntity(1, "Butter", true)
            )
        )
        model.onSuggestionChosen(0)

        repo.update.onNext(Unit)
    }
}
