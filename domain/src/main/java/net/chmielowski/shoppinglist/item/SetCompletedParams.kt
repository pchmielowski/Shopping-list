package net.chmielowski.shoppinglist.item

import net.chmielowski.shoppinglist.Id

data class SetCompletedParams(
    val id: Id,
    val completed: Boolean
)
