package net.chmielowski.shoppinglist.view.items

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.*
import javax.inject.Inject

abstract class ConfirmDialog<T : BaseViewModelFactory<RemoveViewModel>> : DialogFragment() {

    @Inject
    lateinit var modelFactory: T

    private val model by getViewModel { modelFactory }

    companion object {
        fun Fragment.showItemConfirmDialog(id: Id) {
            showConfirmDialog(id, RemoveItemDialog())
        }

        fun Fragment.showShopConfirmDialog(id: Id) {
            showConfirmDialog(id, RemoveShopDialog())
        }

        private fun Fragment.showConfirmDialog(id: Id, dialog: ConfirmDialog<*>) {
            fragmentManager!!.beginTransaction().let { transaction ->
                transaction.addToBackStack(null)
                dialog.arguments = bundleOf(ID to id)
                dialog.show(transaction, null)
            }
        }

        private const val ID = "ID"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.label_are_you_sure)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                model.onRemoveItem(arguments!!.getId(ID))
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()!!
}

class RemoveItemDialog : ConfirmDialog<RemoveViewModel.ForItemFactory>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityComponent.instance.inject(this)
        super.onCreate(savedInstanceState)
    }
}

class RemoveShopDialog : ConfirmDialog<RemoveViewModel.ForShopFactory>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityComponent.instance.inject(this)
        super.onCreate(savedInstanceState)
    }
}

fun Bundle.getId(key: String) = getLong(key)
