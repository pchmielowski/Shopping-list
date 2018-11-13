package net.chmielowski.shoppinglist.view.shops

import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id

data class IconViewModel(val res: Int, override val id: Id) : HasId
