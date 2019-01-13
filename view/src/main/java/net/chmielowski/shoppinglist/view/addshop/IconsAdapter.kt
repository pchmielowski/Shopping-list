package net.chmielowski.shoppinglist.view.addshop

import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.icon_view.*
import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R


class IconsAdapter : BaseListAdapter<IconId, IconViewModel>(R.layout.icon_view) {

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        val margin = margin(holder)
        (holder.itemView.layoutParams as RecyclerView.LayoutParams).run {
            if (holder.adapterPosition == 0) {
                marginStart = margin
            }
            if (holder.adapterPosition == itemCount - 1) {
                marginEnd = margin
            }
        }
        getItem(position).run {
            holder.icon.setImageResource(res)
            holder.icon.isSelected = isSelected
        }
    }

    private fun margin(holder: LayoutContainerViewHolder) =
        holder.itemView.resources.run {
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getDimension(R.dimen.margin), displayMetrics)
        }.toInt()
}
