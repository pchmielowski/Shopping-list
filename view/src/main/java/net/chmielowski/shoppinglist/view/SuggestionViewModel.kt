package net.chmielowski.shoppinglist.view

import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id

data class SuggestionViewModel(override val id: Id, val name: String) : HasId
