package net.chmielowski.shoppinglist.view.items

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.getViewModel
import javax.inject.Inject

class ConfirmDialog : DialogFragment() {

    @Inject
    lateinit var modelFactory: RemoveItemViewModel.Factory

    private val model by getViewModel { modelFactory }

    companion object {
        fun Fragment.showConfirmDialog(item: Id) {
            fragmentManager!!.beginTransaction().let { transaction ->
                transaction.addToBackStack(null)
                val dialog = ConfirmDialog()
                dialog.arguments = bundleOf(ITEM_ID to item)
                dialog.show(transaction, null)
            }
        }

        private const val ITEM_ID = "ITEM_ID"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.label_are_you_sure)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                model.onRemoveItem(arguments!!.getId(ITEM_ID))
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()!!
}

fun Bundle.getId(key: String) = getLong(key)
