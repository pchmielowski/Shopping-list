package net.chmielowski.shoppinglist.view.items

import net.chmielowski.shoppinglist.Id

data class AddItemParams(val name: String, val quantity: String?, val shopId: Id)
