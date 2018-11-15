package net.chmielowski.shoppinglist.view.shops

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.shop_list_fragment.*
import net.chmielowski.shoppinglist.view.BaseFragment
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.ViewComponent
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
        shops.setup()

        model.shops.bindAdapter(shopsAdapter)

        add_shop.onClickNavigateTo(R.id.action_shopList_to_addShop)
    }

    private fun RecyclerView.setup() {
        layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        adapter = shopsAdapter
    }
}