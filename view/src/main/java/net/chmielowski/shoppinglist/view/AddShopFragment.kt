package net.chmielowski.shoppinglist.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_shop_fragment.*
import net.chmielowski.shoppinglist.view.shops.IconViewModel

class AddShopFragment : BaseFragment(R.layout.add_shop_fragment) {
    override fun onInject(component: ViewComponent) {}

    val iconsAdapter = IconsAdapter().also {
        it.submitList(
            listOf(
                IconViewModel(R.drawable.ic_shop_electronic, 0),
                IconViewModel(R.drawable.ic_shop_grocery, 1),
                IconViewModel(R.drawable.ic_shop_pharmacy, 2),
                IconViewModel(R.drawable.ic_shop_sport, 3),
                IconViewModel(R.drawable.ic_shop_stationers, 4)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        choose_icon.setup(this, iconsAdapter, divider = false, orientation = RecyclerView.HORIZONTAL)
        choose_color.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                color_preview.setBackgroundColor(Color.HSVToColor(floatArrayOf(progress.toFloat(), .5f, 1.0f)))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
