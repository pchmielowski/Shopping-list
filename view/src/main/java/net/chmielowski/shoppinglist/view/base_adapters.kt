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
import net.chmielowski.shoppinglist.Id


typealias OnItemClickListener = (Id) -> Unit

class LayoutContainerViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View?
        get() = itemView
}


abstract class BaseListAdapter<T : HasId>(@LayoutRes override val layout: Int) :
    ListAdapter<T, LayoutContainerViewHolder>(DiffCallback()),
    AdapterTrait<T> {
    override var onItemClickListener: OnItemClickListener = {}

    override val item: (Int) -> T? = this::getItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        onCreateViewHolder(parent)
}

interface AdapterTrait<T : HasId> {
    val layout: Int

    var onItemClickListener: OnItemClickListener

    val item: (Int) -> T?

    fun onCreateViewHolder(parent: ViewGroup): LayoutContainerViewHolder =
        parent.inflateChild(layout).let { view ->
            LayoutContainerViewHolder(view)
                .also { holder -> bindClickListener(view, holder) }
        }

    fun bindClickListener(view: View, holder: LayoutContainerViewHolder) {
        view.setOnClickListener {
            item(holder.adapterPosition)?.let {
                onItemClickListener(it.id)
            }
        }
    }
}

fun ViewGroup.inflateChild(@LayoutRes layout: Int) =
    LayoutInflater.from(context)
        .inflate(layout, this, false)!!

class DiffCallback<T : HasId> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ) = oldItem == newItem
}