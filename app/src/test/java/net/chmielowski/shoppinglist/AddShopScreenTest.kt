@file:Suppress("MemberVisibilityCanBePrivate")

package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.Lazy
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.data.shop.AddShop
import net.chmielowski.shoppinglist.view.RealIconViewModelMapper
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel.Result.*
import net.chmielowski.shoppinglist.view.addshop.IconViewModel
import net.chmielowski.shoppinglist.view.helpers.Event
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import net.chmielowski.shoppinglist.view.shops.IconMapper2
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AddShopScreenTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    lateinit var model: AddShopViewModel

    private val dao = ShopDao.Fake()

    @Before
    fun setUp() {
        setupIoSchedulerForTests()
        model = AddShopViewModel(AddShop(Lazy { dao }), RealIconViewModelMapper(IconMapper2.Fake))
    }

    @Test
    fun `choosing icon`() {
        model.onIconClicked(4)
        assertThat(model.icons, hasIconSelected(4))

        model.onIconClicked(2)
        assertThat(model.icons, hasIconSelected(2))

        model.onIconClicked(2)
        assertThat(model.icons, hasIconSelected(2))
    }

    @Test
    fun `empty name entered`() {
        model.onAddingConfirmed()

        model.addingResult shouldHaveValue Event(EmptyName)
    }

    private val color = Pair(2, 1)

    @Test
    fun `adds new shop with success`() {
        model.onNameEntered("Grocery")
        model.onIconClicked(3)
        model.onColorSelected(color)
        model.onAddingConfirmed()

        model.addingResult shouldHaveValue Event(ShopAdded(1))
    }

    @Test
    fun `adds new shop with failure`() {
        model.onNameEntered("Grocery")
        model.onIconClicked(3)
        model.onColorSelected(color)
        dao.failNextInsert()
        model.onAddingConfirmed()

        model.addingResult shouldHaveValue Event(ShopExists)
    }

    private fun hasIconSelected(id: Id) = SmartMatcher<NonNullMutableLiveData<List<IconViewModel>>>(
        "Only $id selected.",
        actual = { it.printSelectedIds() }
    ) { it.iconWithIdIsOnlySelected(id) }

    private fun NonNullMutableLiveData<List<IconViewModel>>.iconWithIdIsOnlySelected(id: Id) =
        value.filter { icon -> icon.isSelected && icon.id == id }.count() == 1

    private fun NonNullMutableLiveData<List<IconViewModel>>.printSelectedIds() =
        value
            .filter(IconViewModel::isSelected)
            .map(IconViewModel::id)
            .toString()
}
