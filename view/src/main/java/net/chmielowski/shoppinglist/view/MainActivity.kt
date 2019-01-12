package net.chmielowski.shoppinglist.view

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val provider by inject<Provider>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provider.instance = this
        setupStrictMode()
        setTheme(R.style.AppTheme)
        setContentView(R.layout.main_activity)
    }

    private fun setupStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
    }

    val onBackPressedListeners = mutableSetOf<() -> Boolean>()

    override fun onBackPressed() {
        if (onBackPressedListeners.none { it() }) {
            super.onBackPressed()
        }
    }

    class Provider {

        lateinit var instance: MainActivity
    }
}
