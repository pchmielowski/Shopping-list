package net.chmielowski.shoppinglist

import io.reactivex.Completable

class ToggleCompleted(private val dao: ItemDao) : CompletableAction<MarkCompletedParams> {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun invoke(params: MarkCompletedParams): Completable {
        return dao.updateCompleted(params.id, params.completed)
    }
}
