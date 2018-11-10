package net.chmielowski.shoppinglist

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.SingleSubject

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemEntity")
    fun findItems(): Single<List<ItemEntity>>

    class Fake : ItemDao {
        override fun findItems() = select

        val insert = CompletableSubject.create()
        val select = SingleSubject.create<List<ItemEntity>>()
    }

}
