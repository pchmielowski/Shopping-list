package net.chmielowski.shoppinglist.view

import kotlinx.android.synthetic.main.item_view.*
import net.chmielowski.shoppinglist.view.items.ItemViewModel

class ItemsAdapter : BaseListAdapter<ItemViewModel>(R.layout.item_view) {

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        val item = getItem(position)
        holder.item_name.text = "${item.name} ${item.quantity ?: ""}"
        holder.item_checked.isChecked = item.completed
    }
}
