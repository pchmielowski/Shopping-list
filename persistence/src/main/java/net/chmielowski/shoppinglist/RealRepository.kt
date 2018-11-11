package net.chmielowski.shoppinglist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface Repository {
    fun findItems(completed: Boolean): Single<List<ItemEntity>>
    fun insert(entity: ItemEntity): Single<Id>
    fun updateCompleted(id: Id, completed: Boolean): Completable

    class Fake : Repository {
        val update = PublishSubject.create<Unit>()
        override fun updateCompleted(id: Id, completed: Boolean) = update.firstOrError().ignoreElement()!!

        val select = PublishSubject.create<List<ItemEntity>>()
        override fun findItems(completed: Boolean): Single<List<ItemEntity>> = select.firstOrError()

        val insert = PublishSubject.create<Id>()
        override fun insert(entity: ItemEntity) = insert.firstOrError()!!
    }
}

class RealRepository @Inject constructor(val dao: ItemDao) : Repository {
    override fun findItems(completed: Boolean): Single<List<ItemEntity>> =
        Single.fromCallable { dao.findItems(completed) }

    override fun insert(entity: ItemEntity): Single<Id> = Single.fromCallable { dao.insert(entity) }

    override fun updateCompleted(id: Id, completed: Boolean): Completable =
        Completable.fromAction { dao.updateCompleted(id, completed) }

}

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemEntity WHERE completed = :completed")
    fun findItems(completed: Boolean): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ItemEntity): Id

    @Query("UPDATE ItemEntity SET completed = :completed WHERE id = :id")
    fun updateCompleted(id: Id, completed: Boolean)
}
