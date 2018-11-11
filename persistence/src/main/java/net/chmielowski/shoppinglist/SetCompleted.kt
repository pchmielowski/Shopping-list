package net.chmielowski.shoppinglist

import javax.inject.Inject

class SetCompleted @Inject constructor(private val repository: Repository) : CompletableAction<SetCompletedParams> {
    override fun invoke(params: SetCompletedParams) = repository.updateCompleted(params.id, params.completed)
}
