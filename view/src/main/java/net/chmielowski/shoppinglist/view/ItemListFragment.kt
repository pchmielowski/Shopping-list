package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.item_list_fragment.*
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
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

        model.run {
            items.bindAdapter(itemsAdapter)
            suggestions.bindAdapter(suggestionsAdapter)
            suggestionsAdapter.onItemClickListener = this::onSuggestionChosen
            new_item_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    model.onNewItemNameChange(s.toString())
                }
            })
            add_new.setOnClickListener { onAddingConfirmed() }
        }
    }
}
