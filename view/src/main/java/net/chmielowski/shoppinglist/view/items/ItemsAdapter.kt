package net.chmielowski.shoppinglist.view.items

import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import kotlinx.android.synthetic.main.item_view.*
import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R


class ItemsAdapter : BaseListAdapter<ItemId, ItemViewModel>(R.layout.item_view) {

    var onCheckedListener: (ItemId, Boolean) -> Unit = { _, _ -> }
    var onDeleteListener: (ItemId) -> Unit = { }

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        val item = getItem(position)
        holder.item_name.text = buildSpannedString {
            append("${item.name}  ")
            italic { append(item.quantity) }
        }
        holder.item_checked.isChecked = item.completed
        holder.item_name.setOnClickListener {
            holder.item_checked.toggle()
        }
        holder.item_checked.setOnCheckedChangeListener { _, isChecked ->
            onCheckedListener(item.id, isChecked)
        }
        holder.delete.setOnClickListener {
            onDeleteListener(item.id)
        }
    }
}
