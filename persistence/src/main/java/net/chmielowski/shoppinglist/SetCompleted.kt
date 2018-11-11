package net.chmielowski.shoppinglist

import io.reactivex.Completable
import javax.inject.Inject

class SetCompleted @Inject constructor(private val dao: Repository) : CompletableAction<SetCompletedParams> {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun invoke(params: SetCompletedParams): Completable {
        return dao.updateCompleted(params.id, params.completed)
    }
}
