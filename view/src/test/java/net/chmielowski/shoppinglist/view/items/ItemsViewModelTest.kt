package net.chmielowski.shoppinglist.view.items

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ItemsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `empty data`() {
        assertThat(ItemsViewModel().items.value, `is`(emptyList()))
    }
}
