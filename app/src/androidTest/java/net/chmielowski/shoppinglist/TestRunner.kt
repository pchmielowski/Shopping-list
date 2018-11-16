package net.chmielowski.shoppinglist


import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

@Suppress("unused") // Used in build.gradle file.
class TestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        loader: ClassLoader,
        name: String,
        context: Context
    ) = super.newApplication(loader, TestApplication::class.java.name, context)!!
}
