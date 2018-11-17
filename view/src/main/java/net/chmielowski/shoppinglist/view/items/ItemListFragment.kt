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
    lateinit var addItemModelFactoryBuilder: AddItemViewModel.Factory.Builder

    private val addItemModel by getViewModel { addItemModelFactoryBuilder.build(shopId) }

    private val shopId
        get() = arguments!!.getLong(getString(R.string.argument_shop_id))

    @Inject
    lateinit var itemsModelFactory: ItemsViewModel.Factory

    private val itemsModel by getViewModel { itemsModelFactory }

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
        }
    }
}
