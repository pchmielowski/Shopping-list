package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ListAdapter
import net.chmielowski.shoppinglist.view.helpers.Event
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes val layout: Int) : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        onInject(ActivityComponent.instance!!)
        super.onCreate(savedInstanceState)
    }

    protected abstract fun onInject(component: ActivityComponent)

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

inline fun <F : BaseViewModelFactory<M>, reified M : ViewModel>
        Fragment.getViewModel(crossinline factory: () -> F) =
    lazy { ViewModelProviders.of(this, factory()).get(M::class.java) }
