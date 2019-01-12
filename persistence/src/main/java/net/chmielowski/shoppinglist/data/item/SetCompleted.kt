package net.chmielowski.shoppinglist.data.item

import dagger.Lazy

import net.chmielowski.shoppinglist.SetCompletedType
import net.chmielowski.shoppinglist.item.SetCompletedParams
import javax.inject.Inject


class SetCompleted @Inject constructor(private val dao: Lazy<ItemDao>) : SetCompletedType {
    override suspend fun invoke(params: SetCompletedParams) =
        dao.get().updateCompleted(params.id, params.completed)
}
