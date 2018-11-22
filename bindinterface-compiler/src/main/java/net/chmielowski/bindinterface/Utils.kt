package net.chmielowski.bindinterface

import com.squareup.kotlinpoet.*
import dagger.Binds
import dagger.Module
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror

internal object Utils {
    @JvmStatic
    fun module(environment: RoundEnvironment): FileSpec {
        val codebase = KPCodebase(environment)
        val bindings = codebase.bindings()
            .map { it.toFunction() }
        return moduleBuilder()
            .add(bindings)
            .toFile()
    }
}

@Suppress("RedundantLambdaArrow")
private fun TypeSpec.Builder.add(functions: Iterable<FunSpec>) = also { _ ->
    functions.forEach { addFunction(it) }
}


sealed class Qualification(val name: String) {
    object Unqualified : Qualification("")
    data class Qualified(val qualifier: Qualifier) : Qualification(qualifier.name)
}

private fun Pair<Implementation, Interface>.combineWithQualifiers() =
    let { (implementation, type) ->
        fun combineWith(qualification: Qualification) = implementation to type to qualification
        implementation
            .qualifiers.let { qualifiers ->
            if (qualifiers.isEmpty()) {
                listOf(combineWith(Qualification.Unqualified))
            } else {
                qualifiers.map { combineWith(Qualification.Qualified(it)) }
            }
        }
    }


private infix fun <A, B, C> Pair<A, B>.to(that: C) = Triple(first, second, that)


private fun Triple<Implementation, Interface, Qualification>.toFunction() =
    let { (implementation, type, qualifier) ->
        type.bindingFunction(implementation, qualifier)
    }

private fun Implementation.combineWithTypes() =
    interfaces.map { type -> this to type }

private fun RoundEnvironment.implementations() =
    getElementsAnnotatedWith(BindInterface::class.java)
        .map { KPImplementation(it) }

private fun TypeSpec.Builder.toFile() =
    FileSpec.builder("net.chmielowski.bindinterface", "GeneratedModule")
        .addType(build())
        .build()

private fun moduleBuilder(): TypeSpec.Builder {
    return TypeSpec.classBuilder("InterfaceBindingsModule")
        .addModifiers(KModifier.ABSTRACT)
        .addAnnotation(Module::class.java)
}

private fun Interface.bindingFunction(implementation: Implementation, qualification: Qualification): FunSpec {
    val typeName = (this as KPInterface).type.toString().className()
    return FunSpec.builder("bind${typeName.simpleName()}${qualification.name}")
        .addAnnotation(Binds::class.java)
        .also {
            if (qualification is Qualification.Qualified) it.addAnnotation((qualification.qualifier as KPQualifier).className)
        }
        .addModifiers(KModifier.ABSTRACT)
        .addParameter("impl", (implementation as KPImplementation).element.toString().className())
        .returns(typeName)
        .build()
}


abstract class Codebase(val environment: RoundEnvironment) {
    abstract fun bindings(): List<Binding>
}

typealias Binding = Triple<Implementation, Interface, Qualification>

class KPCodebase(environment: RoundEnvironment) : Codebase(environment) {
    override fun bindings() =
        environment.implementations()
            .flatMap { it.combineWithTypes() }
            .flatMap { it.combineWithQualifiers() }
}

class KPInterface(type: TypeMirror) : Interface(type)

abstract class Interface(val type: TypeMirror)

interface Qualifier {
    val name: String
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

abstract class Implementation(val element: Element) {
    abstract val qualifiers: List<Qualifier>
    abstract val interfaces: List<Interface>
}

data class KPQualifier(val className: ClassName) : Qualifier {

    override val name
        get() = className.simpleName()
}


fun String.className() = try {
    ClassName.bestGuess(this)
} catch (e: Exception) {
    throw Exception("${this} is invalid class name.", e)
}
