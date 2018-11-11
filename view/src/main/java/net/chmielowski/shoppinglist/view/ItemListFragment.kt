package net.chmielowski.shoppinglist.view

import net.chmielowski.shoppinglist.view.items.ItemsViewModel

class ItemListFragment : BaseFragment<ItemsViewModel, ItemsViewModel.Factory>(
    R.layout.item_list_fragment, ItemsViewModel::class
)
