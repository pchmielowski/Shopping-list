package net.chmielowski.shoppinglist.view

import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.addshop.IconViewModel
import net.chmielowski.shoppinglist.view.addshop.IconViewModelMapper
import net.chmielowski.shoppinglist.view.shops.IconMapper2
import javax.inject.Inject

class RealIconViewModelMapper @Inject constructor(private val iconMapper: IconMapper2) :
    IconViewModelMapper {
    override fun mapToViewModel(id: Id, selectedId: Id) =
        IconViewModel(id, iconMapper.toDrawableRes(id), id == selectedId)
}