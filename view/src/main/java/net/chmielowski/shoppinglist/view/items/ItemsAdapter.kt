package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.item_view.*
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R
import javax.inject.Inject

class ItemsAdapter @Inject constructor() : BaseListAdapter<ItemViewModel>(R.layout.item_view) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        val item = getItem(position)
        holder.item_name.text = "${item.name} ${item.quantity ?: ""}"
        holder.item_checked.isChecked = item.completed
        holder.item_name.setOnClickListener {
            holder.item_checked.toggle()
        }
    }
}
