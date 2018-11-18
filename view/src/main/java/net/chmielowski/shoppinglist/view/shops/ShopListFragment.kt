package net.chmielowski.shoppinglist.view.shops

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.shop_list_fragment.*
import net.chmielowski.shoppinglist.view.BaseFragment
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.ViewComponent
import net.chmielowski.shoppinglist.view.getViewModel
import javax.inject.Inject

class ShopListFragment : BaseFragment(R.layout.shop_list_fragment) {
    override fun onInject(component: ViewComponent) {
        component.inject(this)
    }

    @Inject
    lateinit var shopsAdapter: ShopsAdapter

    @Inject
    lateinit var modelFactory: ShopListViewModel.Factory

    private val model by getViewModel { modelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shop_list.setup()
        model.run {
            shops.bindAdapter(shopsAdapter)
            noShops.observe { empty ->
                no_shops.isVisible = empty
                shop_list.isVisible = !empty
                add_shop.isVisible = !empty
            }
        }
        shopsAdapter.onItemClickListener = { shopId ->
            findNavController().navigate(R.id.itemList, bundleOf(getString(R.string.argument_shop_id) to shopId))
        }
        add_shop.onClickNavigateToAddNew()
        add_first_shop.onClickNavigateToAddNew()
    }

    private fun View.onClickNavigateToAddNew() {
        onClickNavigateTo(R.id.action_shopList_to_addShop)
    }

    private fun RecyclerView.setup() {
        layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        adapter = shopsAdapter
    }
}
