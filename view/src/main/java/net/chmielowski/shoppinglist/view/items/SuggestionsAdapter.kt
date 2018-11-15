package net.chmielowski.shoppinglist.view.items

import kotlinx.android.synthetic.main.suggestion_view.*
import net.chmielowski.shoppinglist.view.BaseListAdapter
import net.chmielowski.shoppinglist.view.LayoutContainerViewHolder
import net.chmielowski.shoppinglist.view.R
import javax.inject.Inject

// TODO: <SuggestionViewModel>
class SuggestionsAdapter @Inject constructor() : BaseListAdapter<ItemViewModel>(R.layout.suggestion_view) {
    override fun onBindViewHolder(holder: LayoutContainerViewHolder, position: Int) {
        holder.name.text = getItem(position).name
    }
}
