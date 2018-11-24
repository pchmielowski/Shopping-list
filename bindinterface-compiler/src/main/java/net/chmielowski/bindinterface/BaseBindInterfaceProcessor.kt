package net.chmielowski.bindinterface

import net.chmielowski.bindinterface.FileFactory.newKotlinFile
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion.latestSupported
import javax.lang.model.element.TypeElement

internal abstract class BaseBindInterfaceProcessor : AbstractProcessor() {
    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        FileFactory.initialize(processingEnv)
    }

    override fun getSupportedOptions() = setOf("kapt.kotlin.generated")

    override fun getSupportedAnnotationTypes() = setOf(BindInterface::class.java.canonicalName)

    override fun getSupportedSourceVersion() = latestSupported()!!

    override fun process(set: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        if (!set.isEmpty()) {
            fileWithModule(roundEnv, set)
                .writeTo(newKotlinFile())
        }
        return false
    }
}
