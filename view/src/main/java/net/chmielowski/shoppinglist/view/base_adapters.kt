package net.chmielowski.shoppinglist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import net.chmielowski.shoppinglist.HasId


typealias OnItemClickListener<T> = (T) -> Unit

class LayoutContainerViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View?
        get() = itemView
}


abstract class BaseListAdapter<I, T : HasId<I>>(@LayoutRes override val layout: Int) :
    ListAdapter<T, LayoutContainerViewHolder>(DiffCallback()),
    AdapterTrait<I, T> {
    override var onItemClickListener: OnItemClickListener<I> = {}

    override val item: (Int) -> T? = this::getItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        onCreateViewHolder(parent)
}

interface AdapterTrait<I, T : HasId<I>> {
    val layout: Int

    var onItemClickListener: OnItemClickListener<I>

    val item: (Int) -> T?

    fun onCreateViewHolder(parent: ViewGroup): LayoutContainerViewHolder =
        parent.inflateChild(layout).let { view ->
            LayoutContainerViewHolder(view)
                .also { holder -> bindClickListener(view, holder) }
        }

    fun bindClickListener(view: View, holder: LayoutContainerViewHolder) {
        view.setOnClickListener {
            item(holder.adapterPosition)?.let { item ->
                onItemClickListener(item.id)
            }
        }
    }
}

fun ViewGroup.inflateChild(@LayoutRes layout: Int) =
    LayoutInflater.from(context)
        .inflate(layout, this, false)!!

class DiffCallback<T : HasId<*>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ) = oldItem == newItem
}