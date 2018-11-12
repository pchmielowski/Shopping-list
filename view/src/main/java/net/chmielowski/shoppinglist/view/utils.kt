package net.chmielowski.shoppinglist.view

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
