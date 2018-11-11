package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_fragment.*
import net.chmielowski.shoppinglist.view.items.ItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemsViewModel

class ItemListFragment : BaseFragment<ItemsViewModel, ItemsViewModel.Factory>(
    R.layout.item_list_fragment, ItemsViewModel::class
) {

    var adapter: ItemsAdapter = ItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        item_list.setup(this, adapter)

        adapter.submitList(
            listOf(
                ItemViewModel(0, "Bread", false),
                ItemViewModel(1, "Butter", false),
                ItemViewModel(2, "Milk", true, "4")
            )
        )
    }
}
