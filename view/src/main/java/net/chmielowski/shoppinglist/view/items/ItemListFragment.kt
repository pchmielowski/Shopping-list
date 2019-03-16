package net.chmielowski.shoppinglist.view.items

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.add_item_view.*
import kotlinx.android.synthetic.main.item_list_content.*
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ItemListFragment : BaseFragment(R.layout.item_list_fragment) {

    private val itemsAdapter by inject<ItemsAdapter>()

    private val addItemModel by viewModel<AddItemViewModel> { shopId }

    private val itemsModel by viewModel<ItemsViewModel> { shopId }

    private val shopId
        get() = parametersOf(
            ShopId(
                arguments!!.getInt(getString(R.string.argument_shop_id))
            )
        )

    private lateinit var onBackPressedListener: () -> Boolean

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        item_list.setup(this, itemsAdapter)
        bottom_sheet.setup()
        remove_list.setOnClickListener {
            ConstraintSet().run {
                clone(requireContext(), R.layout.item_list_fragment_deleting)
                applyTo(root)
            }
            TransitionManager.beginDelayedTransition(root, AutoTransition().apply {
                interpolator = OvershootInterpolator()
            })
            bottom_sheet.isVisible = false
        }

        cancel.setOnClickListener {
            ConstraintSet().run {
                clone(requireContext(), R.layout.item_list_content)
                applyTo(root)
            }
            TransitionManager.beginDelayedTransition(root, AutoTransition().apply {
                interpolator = OvershootInterpolator()
            })
            bottom_sheet.isVisible = true
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

    override fun onDetach() {
        onBackPressedListeners.remove(onBackPressedListener)
        super.onDetach()
    }
}