package net.chmielowski.shoppinglist.view

import kotlinx.android.synthetic.main.icon_view.*
import net.chmielowski.shoppinglist.view.shops.IconViewModel
import javax.inject.Inject

class IconsAdapter @Inject constructor() : BaseListAdapter<IconViewModel>(R.layout.icon_view) {
    init {
        submitList(
            listOf(
                R.drawable.ic_shop_electronic,
                R.drawable.ic_shop_grocery,
                R.drawable.ic_shop_pharmacy,
                R.drawable.ic_shop_sport,
                R.drawable.ic_shop_stationers,
                R.drawable.ic_shop_children,
                R.drawable.ic_shop_business,
                R.drawable.ic_shop_rtv
            ).mapIndexed { i, res -> IconViewModel(i.toLong(), res) }
        )
    }

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        holder.icon.setImageResource(getItem(position).res)
        holder.icon.setOnClickListener { it.isSelected = !it.isSelected }
    }
}
