package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import net.chmielowski.shoppinglist.view.helpers.Event

abstract class BaseFragment(@LayoutRes val layout: Int) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        onInject(ViewComponent.instance)
        super.onCreate(savedInstanceState)
    }

    protected abstract fun onInject(component: ViewComponent)

    protected inline fun <F : BaseViewModelFactory<M>, reified M : ViewModel>
            getViewModel(crossinline factory: () -> F) =
        lazy { ViewModelProviders.of(this, factory()).get(M::class.java) }

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

    protected fun View.onClickNavigateTo(@IdRes action: Int) {
        setOnClickListener {
            findNavController().navigate(action)
        }
    }
}
