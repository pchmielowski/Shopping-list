package net.chmielowski.shoppinglist.view.items

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import net.chmielowski.shoppinglist.FakeWriteAction
import net.chmielowski.shoppinglist.Item
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ItemsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var model: ItemsViewModel

    private lateinit var addItem: FakeWriteAction<Item>

    @Before
    fun setUp() {
        addItem = FakeWriteAction()
        model = ItemsViewModel(addItem, ReadItemsAction(dao))
    }

    @Test
    fun `initially empty list`() {
        model.items shouldContainItems emptyList()
    }

    @Test
    fun `adding element`() {
        model.onTextChange("Bread")

        addItem.subject.onComplete()
        model.items shouldContainItems listOf("Bread")
    }
}

infix fun MutableLiveData<List<ItemViewModel>>.shouldContainItems(names: List<String>) {
    assertThat(this, SmartMatcher(names.toString()) { actual ->
        actual.value!!.map { it.name } == names
    })
}

class SmartMatcher<T>(private val expected: String, private val match: (T) -> Boolean) : BaseMatcher<T>() {
    override fun describeTo(description: Description?) {
        description!!.appendText(expected)
    }

    @Suppress("UNCHECKED_CAST")
    override fun matches(item: Any?) = match(item as T)
}
