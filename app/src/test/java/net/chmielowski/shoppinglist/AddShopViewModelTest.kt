package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import net.chmielowski.shoppinglist.view.shops.AddShopViewModel
import net.chmielowski.shoppinglist.view.shops.IconViewModel
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class AddShopViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `choosing icon`() {
        val model = AddShopViewModel()
        assertThat(model.icons, hasNoIconSelected())

        model.onIconClicked(4)
        assertThat(model.icons, hasIconSelected(4))

        model.onIconClicked(2)
        assertThat(model.icons, hasIconSelected(2))
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