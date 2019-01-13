package net.chmielowski.shoppinglist.view.addshop

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.icon_view.*
import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R


class IconsAdapter : BaseListAdapter<IconId, IconViewModel>(R.layout.icon_view) {

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        (holder.itemView.layoutParams as RecyclerView.LayoutParams).run {
            if (holder.adapterPosition == 0) {
                marginStart *= 2
            }
            if (holder.adapterPosition == itemCount - 1) {
                marginEnd *= 2
            }
        }
        getItem(position).run {
            holder.icon.setImageResource(res)
            holder.icon.isSelected = isSelected
        }
    }
}
