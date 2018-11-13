package net.chmielowski.shoppinglist.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_shop_fragment.*
import javax.inject.Inject

class AddShopFragment : BaseFragment(R.layout.add_shop_fragment) {
    override fun onInject(component: ViewComponent) {
        component.inject(this)
    }

    @Inject
    lateinit var iconsAdapter: IconsAdapter

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
