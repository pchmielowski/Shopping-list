package net.chmielowski.shoppinglist.view.addshop

import kotlinx.android.synthetic.main.icon_view.*
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R
import javax.inject.Inject

class IconsAdapter @Inject constructor() : BaseListAdapter<IconViewModel>(R.layout.icon_view) {
    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        val icon = getItem(position)
        holder.icon.setImageResource(icon.res)
        holder.icon.isSelected = icon.isSelected
    }
}
