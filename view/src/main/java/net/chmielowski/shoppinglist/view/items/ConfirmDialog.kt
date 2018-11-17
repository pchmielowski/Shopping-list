package net.chmielowski.shoppinglist.view.items

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import net.chmielowski.shoppinglist.view.R

class ConfirmDialog : DialogFragment() {
    companion object {
        fun Fragment.showConfirmDialog() {
            fragmentManager!!.beginTransaction().let { transaction ->
                transaction.addToBackStack(null)
                ConfirmDialog().show(transaction, null)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.label_are_you_sure)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel, null)
            .create()!!

}
