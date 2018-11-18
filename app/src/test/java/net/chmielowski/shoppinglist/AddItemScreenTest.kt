package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.Lazy
import net.chmielowski.shoppinglist.data.item.ItemDao
import net.chmielowski.shoppinglist.view.helpers.Event
import net.chmielowski.shoppinglist.view.items.AddItemViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddItemScreenTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var model: AddItemViewModel

    @Before
    fun setUp() {
        setupIoSchedulerForTests()
        model = AddItemViewModel(AddItem(Lazy { ItemDao.Fake() }), 0)
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

        model.addingCompleted shouldHaveValue Event(Unit)
    }
}
