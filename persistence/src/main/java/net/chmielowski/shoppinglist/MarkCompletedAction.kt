package net.chmielowski.shoppinglist

import io.reactivex.Completable

class MarkCompletedAction(private val dao: ItemDao) : WriteAction<MarkCompletedParams> {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun invoke(params: MarkCompletedParams): Completable {
        return dao.updateCompleted(params.id, params.completed)
    }
}
