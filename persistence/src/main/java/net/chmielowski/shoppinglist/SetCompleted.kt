package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.data.item.ItemRepository
import javax.inject.Inject

class SetCompleted @Inject constructor(private val repository: ItemRepository) : CompletableAction<SetCompletedParams> {
    override fun invoke(params: SetCompletedParams) = repository.updateCompleted(params.id, params.completed)
}
