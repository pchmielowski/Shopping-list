package net.chmielowski.shoppinglist.view.items

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.add_item_view_new.*
import kotlinx.android.synthetic.main.item_list_fragment_new.*
import net.chmielowski.shoppinglist.view.*

class NewItemListFragment : BaseItemListFragment(R.layout.item_list_fragment_new) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        item_list.setup(this, itemsAdapter)
        bottom_sheet.setup()
        remove_list.setOnClickListener {
            //            showShopConfirmDialog(shopId)
        }

        itemsAdapter.onDeleteListener = { itemId ->
            //            showItemConfirmDialog(itemId)
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
                it.color?.let { color ->
                    shop_color.backgroundTintList =
                            ColorStateList.valueOf(color)
                }
                shop_color.isVisible = it.colorVisible

            }
            items.bindAdapter(itemsAdapter)

            toggle_shop_completed.setOnCheckedChangeListener { _, isChecked ->
                onToggleShowCompleted(isChecked)
            }
            itemsAdapter.onCheckedListener = { id, completed ->
                onToggled(id, completed)
            }
        }
    }

    private fun View.setup() {
        val addNewLayout = BottomSheetBehavior.from(this)
        onBackPressedListener = {
            if (addNewLayout.isExpanded) {
                addNewLayout.collapse()
                true
            } else false
        }
        onBackPressedListeners.add(onBackPressedListener)
        label_add_new.setOnClickListener {
            addNewLayout.toggleExpanded()
        }
    }
}