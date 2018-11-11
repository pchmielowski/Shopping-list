package net.chmielowski.shoppinglist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

open class ItemDao(val dao: RealItemDao) {
    open fun findItems(completed: Boolean): Single<List<ItemEntity>> = Single.fromCallable { dao.findItems(completed) }

    open fun insert(entity: ItemEntity): Single<Id> = Single.fromCallable { dao.insert(entity) }

    open fun updateCompleted(id: Id, completed: Boolean): Completable =
        Completable.fromAction { dao.updateCompleted(id, completed) }

    class Fake : ItemDao(RealItemDao.NoOpt) {
        val update = PublishSubject.create<Unit>()
        override fun updateCompleted(id: Id, completed: Boolean) = update.firstOrError().ignoreElement()!!

        val select = PublishSubject.create<List<ItemEntity>>()
        override fun findItems(completed: Boolean): Single<List<ItemEntity>> = select.firstOrError()

        val insert = PublishSubject.create<Id>()
        override fun insert(entity: ItemEntity) = insert.firstOrError()!!
    }
}

@Dao
interface RealItemDao {
    @Query("SELECT * FROM ItemEntity WHERE completed = :completed")
    fun findItems(completed: Boolean): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ItemEntity): Id

    @Query("UPDATE ItemEntity SET completed = :completed WHERE id = :id")
    fun updateCompleted(id: Id, completed: Boolean)

    object NoOpt : RealItemDao {
        override fun findItems(completed: Boolean): List<ItemEntity> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun insert(entity: ItemEntity): Id {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun updateCompleted(id: Id, completed: Boolean) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
