package net.chmielowski.shoppinglist.data.item


import net.chmielowski.shoppinglist.SetCompletedType
import net.chmielowski.shoppinglist.item.SetCompletedParams



class SetCompleted(private val dao: ItemDao) : SetCompletedType {
    override suspend fun invoke(params: SetCompletedParams) =
        dao.updateCompleted(params.id, params.completed)
}
