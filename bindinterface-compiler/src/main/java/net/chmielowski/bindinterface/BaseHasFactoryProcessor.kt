package net.chmielowski.bindinterface

import com.squareup.kotlinpoet.asTypeName
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion.latestSupported
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.ExecutableType


internal abstract class BaseHasFactoryProcessor : AbstractProcessor() {
    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        FileFactory.initialize(processingEnv)
    }

    override fun getSupportedOptions() = setOf("kapt.kotlin.generated")

    override fun getSupportedAnnotationTypes() = setOf(HasFactory::class.java.canonicalName)

    override fun getSupportedSourceVersion() = latestSupported()!!

    override fun process(set: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val theClass = (roundEnv.getElementsAnnotatedWith(HasFactory::class.java).firstOrNull() as TypeElement?)
            ?: return false

        val constructor = theClass.enclosedElements
            .filter { it.kind == ElementKind.CONSTRUCTOR }
            .map { (it.asType() as ExecutableType) }
            .single()

        val parameters = constructor.parameterTypes
            .map { it.asTypeName() }
        val parametersLine = parameters
            .mapIndexed { index, typeName -> "arg$index : dagger.Lazy<$typeName>" }
            .joinToString(",\n")

        val output = "class GeneratedFactory($parametersLine) {\n}"

        throw Exception(output)
    }
}

