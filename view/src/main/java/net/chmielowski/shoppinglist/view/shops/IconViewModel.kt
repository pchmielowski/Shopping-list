package net.chmielowski.shoppinglist.view.shops

import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id

data class IconViewModel(override val id: Id, val res: Int) : HasId
