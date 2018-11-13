package net.chmielowski.shoppinglist.view

import kotlinx.android.synthetic.main.icon_view.*
import net.chmielowski.shoppinglist.view.shops.IconViewModel

class IconsAdapter : BaseListAdapter<IconViewModel>(R.layout.icon_view) {
    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        holder.icon.setImageResource(getItem(position).res)
    }
}
