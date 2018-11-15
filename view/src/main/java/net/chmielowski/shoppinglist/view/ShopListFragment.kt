package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.shop_list_fragment.*
import net.chmielowski.shoppinglist.view.shops.ShopsAdapter
import javax.inject.Inject

class ShopListFragment : BaseFragment(R.layout.shop_list_fragment) {
    override fun onInject(component: ViewComponent) {
        component.inject(this)
    }

    @Inject
    lateinit var adapter: ShopsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shops.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        shops.adapter = adapter

        add_shop.setOnClickListener {
            view.findNavController().navigate(R.id.action_shopList_to_addShop)
        }
    }
}