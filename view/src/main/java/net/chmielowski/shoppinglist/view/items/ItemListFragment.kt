package net.chmielowski.shoppinglist.view.items

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.add_item_view.*
import kotlinx.android.synthetic.main.item_list_fragment.*
import net.chmielowski.shoppinglist.ShopColor
import net.chmielowski.shoppinglist.view.*
import net.chmielowski.shoppinglist.view.items.ConfirmDialog.Companion.showItemConfirmDialog
import net.chmielowski.shoppinglist.view.items.ConfirmDialog.Companion.showShopConfirmDialog
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
        get() = arguments!!.getId(getString(R.string.argument_shop_id))

    private val itemsModel by getViewModel { itemsModelFactoryBuilder.build(shopId) }

    override fun onInject(component: ViewComponent) = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        item_list.setup(this, itemsAdapter)
        val addNewLayout = BottomSheetBehavior.from(bottom_sheet)
        item_list.setOnTouchListener { _, _ ->
            addNewLayout.collapse()
            false
        }
        label_add_new.setOnClickListener {
            addNewLayout.toggleExpanded()
        }

        remove_list.setOnClickListener {
            showShopConfirmDialog(shopId)
        }

        itemsAdapter.onDeleteListener = { itemId ->
            showItemConfirmDialog(itemId)
        }

        addItemModel.run {
            newItemNameError.observe {
                new_item_name_layout.error = getString(R.string.error_empty_name)
            }
            addingCompleted.observe {
                new_item_name.reset()
                new_item_quantity.reset()
            }
            new_item_quantity.doOnTextChanged(this::onQuantityChange)
            new_item_name.doOnTextChanged(this::onNewItemNameChange)
            add_new.setOnClickListener { onAddingConfirmed() }
        }
        itemsModel.run {
            shop.observe {
                shop_name.text = it.name
                shop_name.setCompoundDrawablesRelativeWithIntrinsicBounds(it.icon, 0, 0, 0)
                shop_color.showColor(it.color)
            }
            toggle_shop_completed.setOnCheckedChangeListener { _, isChecked ->
                onToggleShowCompleted(isChecked)
            }
            items.bindAdapter(itemsAdapter)
            itemsAdapter.onCheckedListener = { id, completed ->
                onToggled(id, completed)
            }
        }
    }

    private fun FloatingActionButton.showColor(color: ShopColor?) {
        color.toIntColor()?.let {
            backgroundTintList = ColorStateList.valueOf(it)
            isVisible = true
        }
    }
}
