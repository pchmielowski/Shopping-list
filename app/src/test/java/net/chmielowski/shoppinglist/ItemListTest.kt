package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import net.chmielowski.shoppinglist.view.items.ItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ItemListTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var dao: ItemDao.Fake
    private lateinit var model: ItemsViewModel

    @Before
    fun setUp() {
        dao = ItemDao.Fake()
        model = ItemsViewModel(AddItem(dao), ReadItems(dao), ToggleCompleted(dao))
    }

    @Test
    fun `displays suggestions`() {
        dao.select.onNext(emptyList())

        model.onAddNew()
        model.isEnteringNew shouldHaveValue true

        model.onNewItemNameChange("B")
        dao.select.onNext(
            listOf(
                ItemEntity(0, "Bread", true),
                ItemEntity(1, "Butter", true)
            )
        )
        model.suggestions shouldContainItems listOf("Bread", "Butter")

        model.onNewItemNameChange("Br")
        dao.select.onNext(
            listOf(
                ItemEntity(0, "Bread", true)
            )
        )
        model.suggestions shouldContainItems listOf("Bread")
    }

    @Test
    fun `displays new added item`() {
        dao.select.onNext(emptyList())

        model.onAddNew()
        model.onNewItemNameChange("Bread")
        model.onQuantityChange("4")
        model.onAddingConfirmed()
        model.isEnteringNew shouldHaveValue false

        dao.insert.onNext(0)

        model.items shouldHaveValue listOf(
            ItemViewModel(
                0,
                "Bread",
                false,
                "4"
            )
        )

        model.onAddNew()
        model.onNewItemNameChange("Butter")
        model.onAddingConfirmed()
        model.isEnteringNew shouldHaveValue false

        dao.insert.onNext(1)

        model.items shouldHaveValue listOf(
            ItemViewModel(
                0,
                "Bread",
                false,
                "4"
            ),
            ItemViewModel(
                1,
                "Butter",
                false
            )
        )
    }

    @Test
    fun `displays new added item chosen from suggestion`() {
        dao.select.onNext(emptyList())

        model.onAddNew()
        model.onNewItemNameChange("B")
        dao.select.onNext(
            listOf(
                ItemEntity(0, "Bread", true, null),
                ItemEntity(1, "Butter", true, null)
            )
        )
        model.onSuggestionChosen(0)
        model.isEnteringNew shouldHaveValue false

        dao.update.onNext(Unit)

        model.items shouldContainItems listOf("Bread")
    }

    @Test
    fun `marking item as completed and not completed again`() {
        dao.select.onNext(listOf(ItemEntity(0, "Bread")))

        model.onToggled(0)
        dao.update.onNext(Unit)
        model.items shouldHaveValue listOf(ItemViewModel(0, "Bread", true, null))

        model.onToggled(0)
        dao.update.onNext(Unit)
        model.items shouldHaveValue listOf(ItemViewModel(0, "Bread", false, null))
    }
}

private infix fun <T> MutableLiveData<T>.shouldHaveValue(expected: T) {
    assertThat(this.value, SmartMatcher(expected.toString()) {
        it == expected
    })
}


infix fun MutableLiveData<List<ItemViewModel>>.shouldContainItems(names: List<String>) {
    assertThat(this.value, SmartMatcher(names.toString()) { actual ->
        actual!!.map { it.name } == names
    })
}

class SmartMatcher<T>(private val expected: String, private val match: (T) -> Boolean) : BaseMatcher<T>() {
    override fun describeTo(description: Description?) {
        description!!.appendText(expected)
    }

    @Suppress("UNCHECKED_CAST")
    override fun matches(item: Any?) = match(item as T)
}