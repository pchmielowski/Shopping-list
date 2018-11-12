package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.item_list_fragment.*
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
import javax.inject.Inject

class ItemListFragment : BaseFragment<ItemsViewModel, ItemsViewModel.Factory>(
    R.layout.item_list_fragment, ItemsViewModel::class
) {
    @Inject
    lateinit var itemsAdapter: ItemsAdapter

    @Inject
    lateinit var suggestionsAdapter: SuggestionsAdapter

    override fun onInject(component: ViewComponent) = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        item_list.setup(this, itemsAdapter)

        model.run {
            isEnteringNew.observe {
                add_new_item.isVisibleAnimating = !it
                entering_new_item.isVisible = it
            }
            items.bindAdapter(itemsAdapter)
            suggestions.bindAdapter(suggestionsAdapter)
            suggestionsAdapter.onItemClickListener = this::onSuggestionChosen
            add_new_item.setOnClickListener { onAddNew() }
        }
    }
}
