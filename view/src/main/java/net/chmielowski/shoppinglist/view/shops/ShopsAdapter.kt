package net.chmielowski.shoppinglist.view.shops

import android.widget.TextView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.shop_view.*
import net.chmielowski.shoppinglist.view.*
import javax.inject.Inject

class ShopsAdapter @Inject constructor() : BaseListAdapter<ShopViewModel>(R.layout.shop_view) {

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        val shop = getItem(position)
        holder.icon.setImageResource(shop.appearance.icon)
        holder.name.text = shop.appearance.name
        holder.description.setItemsNumber(shop)
        val color = shop.appearance.color
        holder.color.isVisible = color != null
        color.toIntColor()?.let {
            holder.color.setBackgroundColor(it)
        }
    }

    private fun TextView.setItemsNumber(shop: ShopViewModel) {
        text = context.getString(R.string.label_items_number).format(shop.itemsNumber)
    }
}
