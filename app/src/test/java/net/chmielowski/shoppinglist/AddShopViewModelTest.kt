package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import net.chmielowski.shoppinglist.view.shops.AddShopViewModel
import net.chmielowski.shoppinglist.view.shops.IconViewModel
import org.hamcrest.Matcher
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
    }

    private fun hasNoIconSelected(): Matcher<in NonNullMutableLiveData<List<IconViewModel>>>? {
        return SmartMatcher("no selection") {
            it.value.none(IconViewModel::isSelected)
        }
    }
}