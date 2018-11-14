package net.chmielowski.shoppinglist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface ItemRepository {
    fun findItems(completed: Boolean): Single<List<ItemEntity>>

    fun insert(entity: ItemEntity): Single<Id>

    fun updateCompleted(id: Id, completed: Boolean): Completable

    class Fake : ItemRepository {
        val update = PublishSubject.create<Unit>()
        override fun updateCompleted(id: Id, completed: Boolean) = update.firstOrError().ignoreElement()!!

        val select = PublishSubject.create<List<ItemEntity>>()
        override fun findItems(completed: Boolean): Single<List<ItemEntity>> = select.firstOrError()

        val insert = PublishSubject.create<Id>()
        override fun insert(entity: ItemEntity) = insert.firstOrError()!!
    }
}

class RealItemRepository @Inject constructor(private val _dao: Lazy<ItemDao>) : ItemRepository {
    private val dao: Single<ItemDao>
        get() = Single.fromCallable { _dao.get() }.subscribeOn(Schedulers.io())

    override fun findItems(completed: Boolean): Single<List<ItemEntity>> =
        dao.map { it.findItems(completed) }

    override fun insert(entity: ItemEntity): Single<Id> = dao.map { it.insert(entity) }

    override fun updateCompleted(id: Id, completed: Boolean): Completable =
        dao.mapCompletable { it.updateCompleted(id, completed) }
}

fun <T> Single<T>.mapCompletable(mapper: (T) -> Unit) =
    flatMapCompletable { Completable.fromAction { mapper(it) } }!!

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemEntity WHERE completed = :completed")
    fun findItems(completed: Boolean): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ItemEntity): Id

    @Query("UPDATE ItemEntity SET completed = :completed WHERE id = :id")
    fun updateCompleted(id: Id, completed: Boolean)
}
