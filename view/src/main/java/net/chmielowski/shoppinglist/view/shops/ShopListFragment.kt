package net.chmielowski.shoppinglist.view.shops

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.*
import kotlinx.android.synthetic.main.shop_list_fragment.*
import net.chmielowski.shoppinglist.view.BaseFragment
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.addshop.AddShopViewModel
import net.chmielowski.shoppinglist.view.addshop.IconsAdapter
import net.chmielowski.shoppinglist.view.setup
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ShopListFragment : BaseFragment(R.layout.shop_list_fragment) {

    private val shopsAdapter by inject<ShopsAdapter>()

    private val iconsAdapter by inject<IconsAdapter>()

    private val model by viewModel<ShopListViewModel>()

    private val addShopModel by viewModel<AddShopViewModel>()


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
        add_new.setOnClickListener {
            ConstraintSet().run {
                clone(requireContext(), R.layout.shop_list_fragment_adding)
                applyTo(root)
            }
            TransitionManager.beginDelayedTransition(root, object : TransitionSet() {
                init {
                    ordering = ORDERING_SEQUENTIAL
                    addTransition(Fade(Fade.IN))
                        .addTransition(ChangeBounds())
//                        .addTransition(Fade(Fade.OUT))

                }
            }.apply {
                interpolator = OvershootInterpolator()
            })
        }
    }

    private fun RecyclerView.setup() {
        layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        adapter = shopsAdapter
    }
}
