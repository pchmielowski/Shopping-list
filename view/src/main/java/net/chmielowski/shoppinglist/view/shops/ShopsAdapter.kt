package net.chmielowski.shoppinglist.view.shops

import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.shop_view.*
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R


class ShopsAdapter : BaseListAdapter<ShopId, ShopViewModel>(R.layout.shop_view) {

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) =
        holder.bind(getItem(position))

    private fun LayoutContainerViewHolder.bind(shop: ShopViewModel) {
        icon.setImageResource(shop.appearance.icon)
        name.text = shop.appearance.name
        description.text = shop.itemsCount
        color.isVisible = shop.appearance.colorVisible
        shop.appearance.color?.let { color.setBackgroundColor(it) }
    }
}
