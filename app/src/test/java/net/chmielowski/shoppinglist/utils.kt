package net.chmielowski.shoppinglist

import androidx.lifecycle.MutableLiveData
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.MatcherAssert


internal infix fun <T> MutableLiveData<T>.shouldHaveValue(expected: T?) {
    MatcherAssert.assertThat(value, SmartMatcher("$expected") {
        it == expected
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

fun setupIoSchedulerForTests() {
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
}
