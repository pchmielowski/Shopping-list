package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import net.chmielowski.shoppinglist.view.helpers.Event
import org.koin.android.ext.android.inject


abstract class BaseFragment(@LayoutRes val layout: Int) : Fragment() {

    val navigator by inject<Navigator>()

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layout, container, false)!!

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(viewLifecycleOwner, observer)
    }

    protected fun <T> LiveData<Event<T>>.observeNonHandledEvent(observer: (T) -> Unit) {
        observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                observer(it)
            }
        }
    }

    protected fun <T> LiveData<List<T>>.bindAdapter(adapter: ListAdapter<T, *>) {
        observe { adapter.submitList(it) }
    }

}
