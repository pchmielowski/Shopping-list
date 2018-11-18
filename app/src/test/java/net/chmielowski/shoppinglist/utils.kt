package net.chmielowski.shoppinglist

import androidx.lifecycle.MutableLiveData
import net.chmielowski.shoppinglist.view.items.ItemViewModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.MatcherAssert


internal infix fun <T> MutableLiveData<T>.shouldHaveValue(expected: T?) {
    MatcherAssert.assertThat(value, SmartMatcher("$expected") {
        it == expected
    })
}

internal infix fun <T> MutableLiveData<T>.shouldNotHaveValue(notExpected: T?) {
    MatcherAssert.assertThat(value, SmartMatcher("Not $notExpected") {
        it != notExpected
    })
}

// TODO: move to test
infix fun MutableLiveData<List<ItemViewModel>>.shouldContainItems(names: List<String>) {
    MatcherAssert.assertThat(this.value, SmartMatcher(names.toString()) { actual ->
        actual!!.map { it.name } == names
    })
}

class SmartMatcher<T>(
    private val expected: String,
    private val actual: (T) -> String = Any?::toString,
    private val match: (T) -> Boolean
) : BaseMatcher<T>() {
    override fun describeTo(description: Description?) {
        description!!.appendText(expected)
    }

    @Suppress("UNCHECKED_CAST")
    override fun describeMismatch(item: Any?, description: Description?) {
        description!!.appendText("was ").appendValue(actual(item as T))
    }

    @Suppress("UNCHECKED_CAST")
    override fun matches(item: Any?) = match(item as T)
}
