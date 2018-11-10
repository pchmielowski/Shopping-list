package net.chmielowski.shoppinglist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.SingleSubject

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemEntity")
    fun findItems(): Single<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ItemEntity): Completable

    class Fake : ItemDao {
        val select = SingleSubject.create<List<ItemEntity>>()
        override fun findItems() = select

        val insert = CompletableSubject.create()
        override fun insert(entity: ItemEntity) = insert
    }

}
