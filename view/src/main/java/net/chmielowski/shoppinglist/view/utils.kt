package net.chmielowski.shoppinglist.view

import android.app.Activity
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import net.chmielowski.shoppinglist.shop.ShopColor

fun RecyclerView.setup(
    fragment: Fragment,
    adapter: RecyclerView.Adapter<*>,
    divider: Boolean = true,
    orientation: Int = RecyclerView.VERTICAL
) {
    val manager = LinearLayoutManager(fragment.requireContext(), orientation, false)
    this.layoutManager = manager
    this.adapter = adapter
    if (divider) addItemDecoration(DividerItemDecoration(context, manager.orientation))
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer<T> { observer(it) })
}

val Fragment.onBackPressedListeners
    get() = (requireActivity() as MainActivity)
        .onBackPressedListeners

fun ShopColor?.toIntColor() =
    if (this == null) null
    else {
        Color.HSVToColor(
            floatArrayOf(
                first.toFloat() / 8.0f * 360.0f,
                second.toFloat(),
                1.0f
            )
        )
    }

fun EditText.reset() {
    text = null
    hideKeyboard()
}

fun EditText.doOnTextChanged(consumer: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            consumer(s.toString())
        }
    })
}

fun View.hideKeyboard() {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}

fun setKeyboardToHideOnClickOutside(parent: View, view: EditText) {
    if (parent === view) {
        return
    }
    parent.setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            view.hideKeyboard()
        }
        false
    }
    if (parent is ViewGroup) {
        for (i in 0 until parent.childCount) {
            setKeyboardToHideOnClickOutside(parent.getChildAt(i), view)
        }
    }
}

fun BottomSheetBehavior<*>.toggleExpanded() {
    state = if (state == BottomSheetBehavior.STATE_EXPANDED) {
        BottomSheetBehavior.STATE_COLLAPSED
    } else {
        BottomSheetBehavior.STATE_EXPANDED
    }
}

fun BottomSheetBehavior<*>.collapse() {
    state = BottomSheetBehavior.STATE_COLLAPSED
}

val BottomSheetBehavior<*>.isExpanded
    get() = state == BottomSheetBehavior.STATE_EXPANDED
