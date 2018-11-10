package net.chmielowski.shoppinglist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemEntity WHERE completed = :completed")
    fun findItems(completed: Boolean): Single<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ItemEntity): Completable

    class Fake : ItemDao {
        val select = PublishSubject.create<List<ItemEntity>>()
        override fun findItems(completed: Boolean): Single<List<ItemEntity>> = select.firstOrError()

        val insert = PublishSubject.create<Unit>()
        override fun insert(entity: ItemEntity) = insert.firstOrError().ignoreElement()!!
    }

}
