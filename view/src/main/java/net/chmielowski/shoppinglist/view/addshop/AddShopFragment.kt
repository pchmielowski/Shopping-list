package net.chmielowski.shoppinglist.view.addshop

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_shop_fragment.*
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.*
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel.Result.*
import javax.inject.Inject

class AddShopFragment : BaseFragment(R.layout.add_shop_fragment) {
    override fun onInject(component: ViewComponent) {
        component.inject(this)
    }

    @Inject
    lateinit var iconsAdapter: IconsAdapter

    @Inject
    lateinit var modelFactory: AddShopViewModel.Factory

    private val model by getViewModel { modelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        choose_icon.setup(this, iconsAdapter, divider = false, orientation = RecyclerView.HORIZONTAL)
        name.setOnEditorActionListener { _, _, _ ->
            name.hideKeyboard()
            name.clearFocus()
            true
        }
        setKeyboardToHideOnClickOutside(view, name)
        model.run {
            name.doOnTextChanged(model::onNameEntered)
            color_picker.onClickListener = model::onColorSelected
            ok.setOnClickListener {
                onAddingConfirmed()
            }
            iconsAdapter.onItemClickListener = model::onIconClicked
            addingResult.observeNonHandledEvent(this@AddShopFragment::showResult)
            icons.bindAdapter(iconsAdapter)
        }
    }

    private fun showResult(result: AddShopViewModel.Result) {
        when (result) {
            EmptyName -> showError(R.string.error_empty_name)
            ShopExists -> showError(R.string.error_shop_exists)
            is ShopAdded -> navigateToShop(result.newShopId)
        }
    }

    private fun showError(@StringRes string: Int) {
        name_layout.error = getString(string)
    }

    private fun navigateToShop(shopId: Id) {
        view!!.findNavController().navigate(
            R.id.action_addShop_to_itemList,
            bundleOf(getString(R.string.argument_shop_id) to shopId)
        )
    }
}
