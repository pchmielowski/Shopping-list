package net.chmielowski.shoppinglist.view.shops

import android.graphics.Color
import android.widget.TextView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.shop_view.*
import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ShopColor
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R
import javax.inject.Inject

class ShopsAdapter @Inject constructor() : BaseListAdapter<ShopViewModel>(R.layout.shop_view) {

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        val shop = getItem(position)
        holder.icon.setImageResource(shop.icon)
        holder.name.text = shop.name
        holder.description.setItemsNumber(shop)
        holder.color.isVisible = shop.color != null
        shop.color?.let {
            holder.color.setBackgroundColor(toIntColor(it))
        }
    }

    private fun TextView.setItemsNumber(shop: ShopViewModel) {
        text = context.getString(R.string.label_items_number).format(shop.itemsNumber)
    }

    private fun toIntColor(color: ShopColor): Int {
        return Color.HSVToColor(
            floatArrayOf(
                color.first.toFloat() / 8.0f * 360.0f,
                color.second.toFloat(),
                1.0f
            )
        )
    }
}

data class ShopViewModel(
    override val id: Id,
    val name: String,
    val color: ShopColor?,
    val icon: Int,
    val itemsNumber: Int = 0
) : HasId
