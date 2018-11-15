package net.chmielowski.shoppinglist.view.addshop

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_shop_fragment.*
import net.chmielowski.shoppinglist.view.BaseFragment
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.ViewComponent
import net.chmielowski.shoppinglist.view.setup
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

        model.run {
            color_picker.onClickListener = this::onColorSelected
            ok.setOnClickListener {
                onAddingConfirmed()
            }
            addingSuccess.observe { id ->
                view.findNavController().navigate(
                    R.id.action_addShop_to_itemList,
                    bundleOf(getString(R.string.argument_shop_id) to id)
                )
            }
        }
    }
}
