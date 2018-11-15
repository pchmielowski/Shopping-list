package net.chmielowski.shoppinglist.view.items

import kotlinx.android.synthetic.main.item_view.*
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R
import javax.inject.Inject

class ItemsAdapter @Inject constructor() : BaseListAdapter<ItemViewModel>(R.layout.item_view) {

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        val item = getItem(position)
        holder.item_name.text = "${item.name} ${item.quantity ?: ""}"
        holder.item_checked.isChecked = item.completed
    }
}
