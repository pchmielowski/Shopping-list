package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.Lazy
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.chmielowski.shoppinglist.data.item.*
import net.chmielowski.shoppinglist.data.shop.GetShopAppearance
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.data.shop.ShopWithItemsCount
import net.chmielowski.shoppinglist.view.RemoveViewModel
import net.chmielowski.shoppinglist.view.helpers.Event
import net.chmielowski.shoppinglist.view.items.AddItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
import net.chmielowski.shoppinglist.view.RemoveViewModel
import net.chmielowski.shoppinglist.view.shops.ColorMapper
import net.chmielowski.shoppinglist.view.shops.IconMapper
import net.chmielowski.shoppinglist.view.shops.ShopViewModelMapper
import net.chmielowski.shoppinglist.view.shops.Strings
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ItemListScreenTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var itemListModel: ItemsViewModel
    private lateinit var addItemModel: AddItemViewModel
    private lateinit var removeItemModel: RemoveViewModel

    private val shop = 0L
    private lateinit var shopDao: ShopDao.Fake
    private lateinit var itemDao: ItemDao.Fake

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        setupIoSchedulerForTests()
        shopDao = ShopDao.Fake(
            listOf(
                ShopWithItemsCount(
                    shop,
                    "Fake name",
                    null,
                    0,
                    0
                )
            )
        )
        itemDao = ItemDao.Fake()
        itemListModel = ItemsViewModel(
            GetShopAppearance(Lazy { shopDao }),
            ObserveItems(Lazy { itemDao }),
            SetCompleted(Lazy { itemDao }),
            ShopViewModelMapper(
                Strings.Fake,
                ColorMapper.Fake,
                IconMapper.Fake
            ),
            shop,
            Unconfined
        )
        removeItemModel = RemoveViewModel(DeleteItem(Lazy { itemDao }), Unconfined)
        addItemModel = AddItemViewModel(AddItem(Lazy { itemDao }), shop, Unconfined)
    }

    @Test
    fun `marking item as completed`() {
        itemDao.subject.onNext(
            listOf(
                ItemEntity(0, "Bread", shop = shop, quantity = ""),
                ItemEntity(1, "Butter", shop = shop, quantity = "")
            )
        )

        itemListModel.onToggled(0, true)
        itemListModel.items shouldHaveValue listOf(ItemViewModel(1, "Butter", false, quantity = ""))
    }

    @Test
    fun `displays completed items`() {
        itemDao.subject.onNext(
            listOf(
                ItemEntity(1, "Bread", shop = shop, quantity = ""),
                ItemEntity(
                    2,
                    "Butter",
                    shop = shop,
                    quantity = "",
                    completed = true
                )
            )
        )

        itemListModel.onToggleShowCompleted(true)
        itemListModel.items shouldHaveValue listOf(
            ItemViewModel(1, "Bread", false, quantity = ""),
            ItemViewModel(2, "Butter", true, quantity = "")
        )
    }

    @Test
    fun `removing item`() {
        itemDao.subject.onNext(
            listOf(
                ItemEntity(1, "Bread", shop = shop, quantity = "")
            )
        )

        removeItemModel.onRemoveItem(1)
        itemListModel.items shouldHaveValue emptyList()
    }

    @Test
    fun `empty text field error`() {
        addItemModel.onAddingConfirmed()

        addItemModel.newItemNameError shouldHaveValue Event(Unit)
    }

    @Test
    fun `displays new added item`() {
        addItemModel.onNewItemNameChange("Bread")
        addItemModel.onQuantityChange("4")
        addItemModel.onAddingConfirmed()

        addItemModel.addingCompleted shouldHaveValue Event(Unit)
        itemListModel.items shouldHaveValue listOf(ItemViewModel(1, "Bread", false, quantity = "4"))
    }
}
