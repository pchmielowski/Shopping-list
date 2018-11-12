package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<VM : ViewModel, VMF : ViewModelProvider.Factory>(
    @LayoutRes val layout: Int,
    modelClass: KClass<VM>
) : Fragment() {

    // because property is lazy and will be evaluated after the derived class is fully initialized
    @Suppress("LeakingThis")
    protected val model: VM by lazy {
        ViewModelProviders.of(this, factory).get(modelClass.java)
    }

    @Inject
    internal lateinit var factory: VMF

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layout, container, false)!!

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(viewLifecycleOwner, observer)
    }
}
