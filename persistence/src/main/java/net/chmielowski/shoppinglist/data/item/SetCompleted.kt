package net.chmielowski.shoppinglist.data.item

import dagger.Lazy
import net.chmielowski.shoppinglist.CompletableAction
import net.chmielowski.shoppinglist.data.asSingle
import net.chmielowski.shoppinglist.item.SetCompletedParams
import javax.inject.Inject

class SetCompleted @Inject constructor(private val dao: Lazy<ItemDao>) :
    CompletableAction<SetCompletedParams> {
    override fun invoke(params: SetCompletedParams) =
        dao.asSingle()
            .map { it.updateCompleted(params.id, params.completed) }
            .ignoreElement()!!
}
