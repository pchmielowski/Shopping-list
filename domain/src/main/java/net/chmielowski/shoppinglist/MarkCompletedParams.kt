package net.chmielowski.shoppinglist

data class MarkCompletedParams(
    val id: Id,
    val completed: Boolean
)
