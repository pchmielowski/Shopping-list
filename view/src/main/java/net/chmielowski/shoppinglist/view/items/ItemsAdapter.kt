package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import kotlinx.android.synthetic.main.item_view.*
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R


class ItemsAdapter() : BaseListAdapter<ItemViewModel>(R.layout.item_view) {

    var onCheckedListener: (Id, Boolean) -> Unit = { _, _ -> }
    var onDeleteListener: (Id) -> Unit = { }

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
