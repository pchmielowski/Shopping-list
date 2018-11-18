package net.chmielowski.shoppinglist

import dagger.Lazy
import net.chmielowski.shoppinglist.data.item.ItemDao
import javax.inject.Inject

class SetCompleted @Inject constructor(private val dao: Lazy<ItemDao>) : CompletableAction<SetCompletedParams> {
    override fun invoke(params: SetCompletedParams) =
        dao.asSingle()
            .map { it.updateCompleted(params.id, params.completed) }
            .ignoreElement()!!
}
