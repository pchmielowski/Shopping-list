package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityComponent.initializeWith(this)
        super.onCreate(savedInstanceState)
        setupStrictMode()
        setTheme(R.style.AppTheme)
        setContentView(R.layout.main_activity)
    }

    private fun setupStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .build()
        )
    }

    val onBackPressedListeners = mutableSetOf<() -> Boolean>()

    override fun onBackPressed() {
        if (onBackPressedListeners.none { it() }) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityComponent.release()
    }
}
