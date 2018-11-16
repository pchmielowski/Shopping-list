package net.chmielowski.shoppinglist.view.shops

import android.graphics.Color
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

    init {
        submitList(
            listOf(
                ShopViewModel(0, "Tesco", 1 to 1, R.drawable.ic_shop_grocery),
                ShopViewModel(1, "Tesco", 2 to 1, R.drawable.ic_shop_business),
                ShopViewModel(2, "Tesco", 3 to 1, R.drawable.ic_shop_children),
                ShopViewModel(3, "Tesco", 4 to 1, R.drawable.ic_shop_pharmacy),
                ShopViewModel(4, "Tesco", 5 to 1, R.drawable.ic_shop_electronic)
            )
        )
    }

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        val shop = getItem(position)
        holder.icon.setImageResource(shop.icon)
        holder.name.text = shop.name
        holder.color.isVisible = shop.color != null
        shop.color?.let {
            holder.color.setBackgroundColor(toIntColor(it))
        }
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
    val icon: Int
) : HasId
