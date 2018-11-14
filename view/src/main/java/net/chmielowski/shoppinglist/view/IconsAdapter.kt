package net.chmielowski.shoppinglist.view

import kotlinx.android.synthetic.main.icon_view.*
import net.chmielowski.shoppinglist.view.shops.IconViewModel
import javax.inject.Inject

class IconsAdapter @Inject constructor() : BaseListAdapter<IconViewModel>(R.layout.icon_view) {
    init {
        submitList(LongRange(0, 7).map { IconViewModel.fromId(it) })
    }

    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        holder.icon.setImageResource(getItem(position).res)
        holder.icon.setOnClickListener { it.isSelected = !it.isSelected }
    }
}
