@file:Suppress("MemberVisibilityCanBePrivate")

package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.chmielowski.shoppinglist.shop.AddShop
import net.chmielowski.shoppinglist.shop.ShopRepository
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel
import net.chmielowski.shoppinglist.view.addshop.IconViewModel
import net.chmielowski.shoppinglist.view.helpers.Event
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddShopScreenTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    lateinit var model: AddShopViewModel

    lateinit var repo: ShopRepository.Fake

    @Before
    fun setUp() {
        repo = ShopRepository.Fake()
        model = AddShopViewModel(AddShop(repo))
    }

    @Test
    fun `choosing icon`() {
        model.onIconClicked(4)
        assertThat(model.icons, hasIconSelected(4))

        model.onIconClicked(2)
        assertThat(model.icons, hasIconSelected(2))

        model.onIconClicked(2)
        assertThat(model.icons, hasNoIconSelected())
    }

    @Test
    fun `empty name entered`() {
        model.onAddingConfirmed()

        model.nameError shouldHaveValue Event(Unit)
    }

    private val color = Pair(2, 1)

    @Test
    fun `adds new shop with success`() {
        model.onNameEntered("Grocery")
        model.onIconClicked(3)
        model.onColorSelected(color)
        model.onAddingConfirmed()

        repo.add.onNext(2)

        model.addingSuccess shouldHaveValue Event(2L)
    }

    @Test
    fun `adds new shop with failure`() {
        model.onNameEntered("Grocery")
        model.onIconClicked(3)
        model.onColorSelected(color)
        model.onAddingConfirmed()

        repo.add.onError(Exception())

        model.addingSuccess shouldHaveValue noEvent
    }

    private val noEvent = null

    private fun hasNoIconSelected() =
        SmartMatcher<NonNullMutableLiveData<List<IconViewModel>>>("No selection.") {
            it.value.none(IconViewModel::isSelected)
        }

    private fun hasIconSelected(id: Id) =
        SmartMatcher<NonNullMutableLiveData<List<IconViewModel>>>("Only $id selected.") {
            it.value.filter(IconViewModel::isSelected).all { icon -> icon.id == id }
        }
}
