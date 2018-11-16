package net.chmielowski.shoppinglist.data.shop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.chmielowski.shoppinglist.AppDatabase
import net.chmielowski.shoppinglist.shop.ShopEntity
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// TODO: fix test

@RunWith(AndroidJUnit4::class)
class ShopDaoTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Ignore
    @Test
    fun name() {
        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).build()

        val entity = ShopEntity("Name", null, 0)
        val dao = db.shopDao
        val test = dao.getAll().test()
        dao.insert(entity)

        test.assertValue(listOf(entity))
    }
}