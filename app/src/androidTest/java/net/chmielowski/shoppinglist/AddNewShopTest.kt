package net.chmielowski.shoppinglist


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import net.chmielowski.shoppinglist.shop.ShopRepository
import net.chmielowski.shoppinglist.view.MainActivity
import net.chmielowski.shoppinglist.view.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddNewShopTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Inject
    lateinit var repository: ShopRepository

    @Test
    fun mainActivityTest() {

        onView(withId(R.id.add_shop))
            .perform(click())

        val shopName = "The Shop"
        onView(withId(R.id.name))
            .perform(replaceText(shopName), closeSoftKeyboard())

        onView(withId(R.id.ok))
            .perform(click())

        pressBack()

//        onView(withText(shopName))
//            .check(matches(isDisplayed()))
    }
}
