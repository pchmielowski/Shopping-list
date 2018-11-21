package net.chmielowski.bindinterface

import java.io.File
import java.lang.Exception
import java.util.Objects

import javax.annotation.processing.ProcessingEnvironment

internal object FileFactory {

    private var environment: ProcessingEnvironment? = null

    @JvmStatic
    fun initialize(env: ProcessingEnvironment) {
        environment = env
    }

    @JvmStatic
    fun newKotlinFile(): File {
        val env: ProcessingEnvironment = environment ?: throw Exception("ProcessingEnvironment not initialized.")
        val directory: String = env.options["kapt.kotlin.generated"]
            ?: throw Exception("Can't find the target directory for generated Kotlin files.")
        return File(directory)
    }
}
