package net.chmielowski.shoppinglist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.main_activity)
    }

    val onBackPressedListeners = mutableSetOf<() -> Boolean>()

    override fun onBackPressed() {
        if (onBackPressedListeners.none { it() }) {
            super.onBackPressed()
        }
    }
}
