@file:Suppress("MemberVisibilityCanBePrivate")

package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.Lazy
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.shop.AddShop
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel.Result.*
import net.chmielowski.shoppinglist.view.addshop.IconViewModel
import net.chmielowski.shoppinglist.view.helpers.Event
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
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
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        model = AddShopViewModel(AddShop(Lazy { dao }))
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
