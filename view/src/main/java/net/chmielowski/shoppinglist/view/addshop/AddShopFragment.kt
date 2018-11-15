package net.chmielowski.shoppinglist.view.addshop

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
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
            choose_color.doOnProgressChanged { progress ->
                onColorSelected(progress.toFloat())
            }
            color.observe {
                color_preview.backgroundTintList = color(it)
            }
            ok.setOnClickListener {
                onAddingConfirmed()
            }
        }
    }

    private fun color(progress: Float) =
        ColorStateList.valueOf(Color.HSVToColor(floatArrayOf(progress, .5f, 1.0f)))
}

// TODO: move to utils
fun SeekBar.doOnProgressChanged(action: (Int) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            action(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
}
