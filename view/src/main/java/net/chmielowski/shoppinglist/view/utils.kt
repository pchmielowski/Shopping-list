package net.chmielowski.shoppinglist.view

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setup(fragment: Fragment, adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = LinearLayoutManager(fragment.requireContext())
    this.adapter = adapter
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer<T> { observer(it) })
}

var View.isVisibleAnimating: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        if (value) {
            scaleX = 0.0f
            scaleY = 0.0f
            visibility = View.VISIBLE
            animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .start()
        } else {
            animate()
                .scaleX(0.0f)
                .scaleY(0.0f)
                .withEndAction { visibility = View.GONE }
                .start()
        }
    }