package net.chmielowski.shoppinglist.view.items

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.item_list_fragment.*
import net.chmielowski.shoppinglist.view.*
import javax.inject.Inject

class ItemListFragment : BaseFragment(R.layout.item_list_fragment) {
    @Inject
    lateinit var itemsAdapter: ItemsAdapter

    @Inject
    lateinit var suggestionsAdapter: SuggestionsAdapter

    @Inject
    lateinit var modelFactory: ItemsViewModel.Factory

    private val model by getViewModel { modelFactory }

    override fun onInject(component: ViewComponent) = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        item_list.setup(this, itemsAdapter)

        label_add_new.setOnClickListener {
            BottomSheetBehavior.from(bottom_sheet).toggleExpanded()
        }

        model.run {
            items.bindAdapter(itemsAdapter)
            suggestions.bindAdapter(suggestionsAdapter)
            suggestionsAdapter.onItemClickListener = this::onSuggestionChosen
            new_item_name.doOnTextChanged(this::onNewItemNameChange)
            add_new.setOnClickListener { onAddingConfirmed() }
        }
    }
}
