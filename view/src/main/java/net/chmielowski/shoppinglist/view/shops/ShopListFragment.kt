package net.chmielowski.shoppinglist.view.shops

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.shop_list_fragment.*
import net.chmielowski.shoppinglist.view.BaseFragment
import net.chmielowski.shoppinglist.view.R
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ShopListFragment : BaseFragment(R.layout.shop_list_fragment) {

    private val shopsAdapter by inject<ShopsAdapter>()

    private val model by viewModel<ShopListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shop_list.setup()
        model.run {
            shops.bindAdapter(shopsAdapter)
        }
        shopsAdapter.onItemClickListener = { shopId ->
            navigator.toItemList(shopId)
        }
        add_new.setOnClickListener {

        }
    }

    private fun RecyclerView.setup() {
        layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        adapter = shopsAdapter
    }
}
