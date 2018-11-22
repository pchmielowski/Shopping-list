package net.chmielowski.bindinterface

import com.squareup.kotlinpoet.*
import dagger.Binds
import dagger.Module
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror


fun fileWithModule(environment: RoundEnvironment): FileSpec {
    return moduleBuilder()
        .add(
            KPCodebase(environment)
                .bindings()
                .map(Binding::toFunction)
        )
        .toFile()
}

fun Binding.toFunction() =
    let { (implementation, type, qualifier) ->
        val typeName = (type as KPInterface).type.toString().className()
        FunSpec.builder("bind${typeName.simpleName()}${qualifier.name}")
            .addAnnotation(Binds::class.java)
            .also {
                if (qualifier is Qualification.Qualified) it.addAnnotation((qualifier.qualifier as KPQualifier).className)
            }
            .addModifiers(KModifier.ABSTRACT)
            .addParameter("impl", (implementation as KPImplementation).element.toString().className())
            .returns(typeName)
            .build()
    }

class KPImplementation(element: Element) : Implementation(element) {
    override val interfaces: List<Interface>
        get() = (element as TypeElement)
            .interfaces
            .map { KPInterface(it) }
    override val qualifiers
        get() = element.getAnnotation(BindInterface::class.java)
            .qualifiers
            .map { it.className() }
            .map { KPQualifier(it) }
}

fun String.className() = try {
    ClassName.bestGuess(this)
} catch (e: Exception) {
    throw Exception("${this} is invalid class name.", e)
}

data class KPQualifier(val className: ClassName) : Qualifier {

    override val name
        get() = className.simpleName()
}

class KPCodebase(environment: RoundEnvironment) : Codebase(environment) {
    override fun bindings() =
        environment.implementations()
            .flatMap { it.combineWithTypes() }
            .flatMap { it.combineWithQualifiers() }
}

class KPInterface(type: TypeMirror) : Interface(type)

private fun TypeSpec.Builder.toFile() =
    FileSpec.builder("net.chmielowski.bindinterface", "GeneratedModule")
        .addType(build())
        .build()

private fun moduleBuilder(): TypeSpec.Builder {
    return TypeSpec.classBuilder("InterfaceBindingsModule")
        .addModifiers(KModifier.ABSTRACT)
        .addAnnotation(Module::class.java)
}


@Suppress("RedundantLambdaArrow")
private fun TypeSpec.Builder.add(functions: Iterable<FunSpec>) = also { _ ->
    functions.forEach { addFunction(it) }
}
