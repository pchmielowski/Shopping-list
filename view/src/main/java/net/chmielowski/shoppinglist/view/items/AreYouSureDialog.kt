package net.chmielowski.shoppinglist.view.items

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import net.chmielowski.shoppinglist.view.R

class AreYouSureDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(requireActivity())
            .setMessage(R.string.label_are_you_sure)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel, null)
            .create()!!

}
