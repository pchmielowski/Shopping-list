package net.chmielowski.shoppinglist.view

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setup(fragment: Fragment, adapter: RecyclerView.Adapter<*>) {
    val manager = LinearLayoutManager(fragment.requireContext())
    this.layoutManager = manager
    this.adapter = adapter
    addItemDecoration(DividerItemDecoration(context, manager.orientation))
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer<T> { observer(it) })
}
