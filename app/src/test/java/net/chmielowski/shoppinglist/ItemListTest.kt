package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import net.chmielowski.shoppinglist.view.items.ItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test

class ItemListTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `adding item`() {
        val dao = ItemDao.Fake()

        val model = ItemsViewModel(AddItemAction(dao), ReadItemsAction(dao))

        model.addItem("Bread")

        dao.insert.onComplete()
        dao.select.onSuccess(listOf(ItemEntity(0, "Bread")))

        model.items shouldContainItems listOf("Bread")
    }
}


infix fun MutableLiveData<List<ItemViewModel>>.shouldContainItems(names: List<String>) {
    MatcherAssert.assertThat(this, SmartMatcher(names.toString()) { actual ->
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
