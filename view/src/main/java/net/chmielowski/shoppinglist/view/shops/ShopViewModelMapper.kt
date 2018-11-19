package net.chmielowski.shoppinglist.view.shops

import net.chmielowski.shoppinglist.shop.Shop
import net.chmielowski.shoppinglist.shop.ShopAppearance
import net.chmielowski.shoppinglist.view.R
import javax.inject.Inject

class ShopViewModelMapper @Inject constructor(
    private val strings: Strings,
    private val colorMapper: ColorMapper,
    private val iconMapper: IconMapper2
) {
    fun toViewModels(shops: Iterable<Shop>) =
        shops.map { it.toViewModel() }

    private fun Shop.toViewModel() =
        ShopViewModel2(
            id,
            strings.format(R.string.label_items_number, itemsCount),
            toAppearance(this)
        )

    fun toAppearance(shop: Shop) =
        ShopViewModel2.Appearance(
            shop.name,
            iconMapper.toDrawableRes(shop.icon.id),
            shop.color != null,
            shop.color?.let { colorMapper.toInt(it) }
        )

    fun toAppearance(shop: ShopAppearance) =
        ShopViewModel2.Appearance(
            shop.name,
            iconMapper.toDrawableRes(shop.icon.id),
            shop.color != null,
            shop.color?.let { colorMapper.toInt(it) }
        )
}
