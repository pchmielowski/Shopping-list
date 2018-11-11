package net.chmielowski.shoppinglist.view

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setup(fragment: Fragment, adapter: RecyclerView.Adapter<*>) {
    val context = fragment.requireContext()
    this.layoutManager = LinearLayoutManager(context)
    this.adapter = adapter
}