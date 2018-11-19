package net.chmielowski.shoppinglist.view.addshop

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_shop_fragment.*
import net.chmielowski.shoppinglist.view.*
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel.Result.*
import javax.inject.Inject

class AddShopFragment : BaseFragment(R.layout.add_shop_fragment) {
    override fun onInject(component: ActivityComponent) {
        component.inject(this)
    }

    @Inject
    lateinit var iconsAdapter: IconsAdapter

    @Inject
    lateinit var modelFactory: AddShopViewModel.Factory

    @Inject
    lateinit var navigator: Navigator

    private val model by getViewModel { modelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        choose_icon.setup(this, iconsAdapter, divider = false, orientation = RecyclerView.HORIZONTAL)
        choose_icon.showAnimation()
        name.setOnEditorActionListener { _, _, _ ->
            name.hideKeyboard()
            name.clearFocus()
            true
        }
        setKeyboardToHideOnClickOutside(view, name)
        model.run {
            addingResult.observeNonHandledEvent(this@AddShopFragment::showResult)
            icons.bindAdapter(iconsAdapter)

            name.doOnTextChanged(model::onNameEntered)
            color_picker.onClickListener = model::onColorSelected
            ok.setOnClickListener { onAddingConfirmed() }
            iconsAdapter.onItemClickListener = model::onIconClicked
        }
    }

    private fun RecyclerView.showAnimation() {
        val dx = 100
        val time = 750L
        postDelayed({ smoothScrollBy(dx, 0) }, time)
        postDelayed({ smoothScrollBy(-dx, 0) }, 2 * time)
    }

    private fun showResult(result: AddShopViewModel.Result) {
        when (result) {
            EmptyName -> showError(R.string.error_empty_name)
            ShopExists -> showError(R.string.error_shop_exists)
            is ShopAdded -> navigator.toItemList(result.newShopId)
        }
    }

    private fun showError(@StringRes string: Int) {
        name_layout.error = getString(string)
    }
}
