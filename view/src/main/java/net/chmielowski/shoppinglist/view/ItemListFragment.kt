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
    lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewComponent.instance.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        item_list.setup(this, adapter)

        adapter.submitList(
            listOf(
                ItemViewModel(0, "Bread", false),
                ItemViewModel(1, "Butter", false),
                ItemViewModel(2, "Milk", true, "4")
            )
        )

        model.isEnteringNew.observe(this) {
            entering_new_item.isVisible = it
        }

        add_new_item.setOnClickListener {
            model.onAddNew()
        }
    }
}
