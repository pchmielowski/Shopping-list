package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Single
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import net.chmielowski.shoppinglist.view.shops.AddShopParams
import net.chmielowski.shoppinglist.view.shops.AddShopResult
import net.chmielowski.shoppinglist.view.shops.AddShopViewModel
import net.chmielowski.shoppinglist.view.shops.IconViewModel
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class AddShopViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    lateinit var model: AddShopViewModel

    class AddShopAction : ActionWithResult<AddShopParams, AddShopResult> {
        override fun invoke(params: AddShopParams): Single<AddShopResult> = Single.just(AddShopResult.Success)
    }

    @Before
    fun setUp() {
        model = AddShopViewModel(AddShopAction())
    }

    @Test
    fun `choosing icon`() {
        assertThat(model.icons, hasNoIconSelected())

        model.onIconClicked(4)
        assertThat(model.icons, hasIconSelected(4))

        model.onIconClicked(2)
        assertThat(model.icons, hasIconSelected(2))

        model.onIconClicked(2)
        assertThat(model.icons, hasNoIconSelected())
    }

    @Test
    fun `choosing color`() {
        model.onColorSelected(0.4f)
        model.color shouldHaveValue 0.4f
    }

    @Test
    fun `empty name entered`() {
        model.onAddingConfirmed()

        model.nameError shouldHaveValue Event(Unit)
    }

    @Test
    fun `adds new shop with success`() {
        model.onNameEntered("Grocery")
        model.onIconClicked(3)
        model.onColorSelected(0.3f)
        model.onAddingConfirmed()

        // repo.addShop.onNext()

        model.addingSuccess shouldHaveValue Event(Unit)
    }

    @Ignore
    @Test
    fun `adds new shop with failure`() {
        model.onNameEntered("Grocery")
        model.onIconClicked(3)
        model.onColorSelected(100.0f)
        model.onAddingConfirmed()

        // repo.addShop.onError() - shop already exists
    }

    private fun hasNoIconSelected() =
        SmartMatcher<NonNullMutableLiveData<List<IconViewModel>>>("No selection.") {
            it.value.none(IconViewModel::isSelected)
        }

    private fun hasIconSelected(id: Id) =
        SmartMatcher<NonNullMutableLiveData<List<IconViewModel>>>("Only $id selected.") {
            it.value.filter(IconViewModel::isSelected).all { icon -> icon.id == id }
        }
}