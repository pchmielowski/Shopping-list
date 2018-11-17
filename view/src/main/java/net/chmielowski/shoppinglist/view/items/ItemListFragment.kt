package net.chmielowski.shoppinglist.view.items

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.add_item_view.*
import kotlinx.android.synthetic.main.item_list_fragment.*
import net.chmielowski.shoppinglist.view.*
import javax.inject.Inject

class ItemListFragment : BaseFragment(R.layout.item_list_fragment) {
    @Inject
    lateinit var itemsAdapter: ItemsAdapter

    @Inject
    lateinit var addItemModelFactoryBuilder: AddItemViewModel.Factory.Builder

    private val addItemModel by getViewModel { addItemModelFactoryBuilder.build(shopId) }

    @Inject
    lateinit var itemsModelFactoryBuilder: ItemsViewModel.Factory.Builder

    private val shopId
        get() = arguments!!.getLong(getString(R.string.argument_shop_id))

    private val itemsModel by getViewModel { itemsModelFactoryBuilder.build(shopId) }

    override fun onInject(component: ViewComponent) = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        item_list.setup(this, itemsAdapter)

        label_add_new.setOnClickListener {
            BottomSheetBehavior.from(bottom_sheet).toggleExpanded()
        }

        addItemModel.run {
            newItemNameError.observe {
                new_item_name.error = getString(R.string.error_empty_name)
            }
            addingCompleted.observe {
                new_item_name.reset()
                new_item_quantity.reset()
            }
            new_item_name.doOnTextChanged(this::onNewItemNameChange)
            add_new.setOnClickListener { onAddingConfirmed() }
        }
        itemsModel.run {
            items.bindAdapter(itemsAdapter)
            itemsAdapter.onCheckedListener = { id, completed ->
                onToggled(id, completed)
            }
        }
    }
}
