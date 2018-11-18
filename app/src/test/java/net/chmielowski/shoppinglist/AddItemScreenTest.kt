package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.chmielowski.shoppinglist.view.helpers.Event
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
        model = AddItemViewModel(AddItem(repo), 0)
    }

    @Test
    fun `empty text field error`() {
        model.onAddingConfirmed()

        model.newItemNameError shouldHaveValue Event(Unit)
    }

    @Test
    fun `displays new added item`() {
        model.onNewItemNameChange("Bread")
        model.onQuantityChange("4")
        model.onAddingConfirmed()

        repo.insert.onNext(0)

        model.addingCompleted shouldHaveValue Event(Unit)
    }
}
