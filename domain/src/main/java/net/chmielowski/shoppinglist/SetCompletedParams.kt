package net.chmielowski.shoppinglist

data class SetCompletedParams(
    val id: Id,
    val completed: Boolean
)
