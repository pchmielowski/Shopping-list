package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.item_list_fragment.*
import net.chmielowski.shoppinglist.view.items.ItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
import javax.inject.Inject

class ItemListFragment : BaseFragment<ItemsViewModel, ItemsViewModel.Factory>(
    R.layout.item_list_fragment, ItemsViewModel::class
) {
    @Inject
    lateinit var itemsAdapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewComponent.instance.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        item_list.setup(this, itemsAdapter)

        model.run {
            isEnteringNew.observe {
                add_new_item.isVisibleAnimating = !it
                entering_new_item.isVisible = it
            }
            items.observe {
                itemsAdapter.submitList(it)
            }
            suggestions.observe {
                // TODO
            }
            add_new_item.setOnClickListener {
                onAddNew()
            }
        }
    }
}

var View.isVisibleAnimating: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        if (value) {
            scaleX = 0.0f
            scaleY = 0.0f
            visibility = View.VISIBLE
            animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .start()
        } else {
            animate()
                .scaleX(0.0f)
                .scaleY(0.0f)
                .withEndAction { visibility = View.GONE }
                .start()
        }
    }
