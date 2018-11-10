package net.chmielowski.shoppinglist.view.items

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*

class ItemsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `initially empty list`() {
        assertThat(ItemsViewModel().items.value, `is`(emptyList()))
    }

    @Test
    fun `adding element`() {
        val model = ItemsViewModel()

        model.addItem("Bread")

        assertThat(model.items.value, hasItemsWithNames("Bread"))
    }

    private fun hasItemsWithNames(vararg names: String): Matcher<in List<ItemViewModel>?>? {
        return object : BaseMatcher<List<ItemViewModel>>() {
            override fun describeTo(description: Description?) {
                description!!.appendText(Arrays.toString(names))
            }

            override fun matches(item: Any?): Boolean {
                return (item as List<ItemViewModel>).map { it.name } == names.toList().toList()
            }
        }
    }
}
