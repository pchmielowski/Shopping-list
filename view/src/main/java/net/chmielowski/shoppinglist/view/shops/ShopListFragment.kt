package net.chmielowski.shoppinglist.view.shops

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import kotlinx.android.synthetic.main.shop_list_fragment.*
import net.chmielowski.shoppinglist.view.BaseFragment
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel
import net.chmielowski.shoppinglist.view.addshop.IconsAdapter
import net.chmielowski.shoppinglist.view.setup
import net.chmielowski.shoppinglist.view.shops.ShopListFragment.State.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ShopListFragment : BaseFragment(R.layout.shop_list_fragment) {

    private val shopsAdapter by inject<ShopsAdapter>()

    private val iconsAdapter by inject<IconsAdapter>()

    private val model by viewModel<ShopListViewModel>()

    private val addShopModel by viewModel<AddShopViewModel>()

    enum class State {
        DISPLAYING_SHOPS,
        ENTERING_NEW_SHOP_NAME,
        CHOOSING_ICON_AND_COLOR
    }

    private var state = DISPLAYING_SHOPS

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shop_list.setup()
        choose_icon.setup(this, iconsAdapter, divider = false, orientation = RecyclerView.HORIZONTAL)
        model.run {
            shops.bindAdapter(shopsAdapter)
        }
        addShopModel.run {
            icons.bindAdapter(iconsAdapter)
        }

        shopsAdapter.onItemClickListener = { shopId ->
            navigator.toItemList(shopId)
        }
        cancel.setOnClickListener {
            transitionTo(R.layout.shop_list_fragment)
            state = DISPLAYING_SHOPS
        }
        add_new.setOnClickListener {
            when (state) {
                DISPLAYING_SHOPS -> {
                    transitionTo(R.layout.shop_list_fragment_adding)
                    state = ENTERING_NEW_SHOP_NAME
                }
                ENTERING_NEW_SHOP_NAME -> {
                    transitionTo(R.layout.shop_list_fragment_choosing_color)
                    state = CHOOSING_ICON_AND_COLOR
                }
                CHOOSING_ICON_AND_COLOR -> {
                    transitionTo(R.layout.shop_list_fragment_adding)
                    state = DISPLAYING_SHOPS
                }
            }
        }
    }

    private fun transitionTo(@LayoutRes layout: Int) {
        ConstraintSet().run {
            clone(requireContext(), layout)
            applyTo(root)
        }
        val set = TransitionSet().apply {
            ordering = TransitionSet.ORDERING_SEQUENTIAL
            addTransition(Fade(Fade.IN))
                .addTransition(ChangeBounds())
            interpolator = OvershootInterpolator()
        }
        TransitionManager.beginDelayedTransition(root, set)
    }

    private fun RecyclerView.setup() {
        layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        adapter = shopsAdapter
    }
}
