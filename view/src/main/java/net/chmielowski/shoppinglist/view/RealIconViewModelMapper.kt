package net.chmielowski.shoppinglist.view

import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.addshop.IconViewModel
import net.chmielowski.shoppinglist.view.addshop.IconViewModelMapper
import net.chmielowski.shoppinglist.view.shops.IconMapper


class RealIconViewModelMapper(private val iconMapper: IconMapper) :
    IconViewModelMapper {
    override fun mapToViewModel(id: Id, selectedId: Id) =
        IconViewModel(id, iconMapper.toDrawableRes(id), id == selectedId)
}