package net.chmielowski.bindinterface

import com.squareup.kotlinpoet.*
import dagger.Binds
import dagger.Module
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror

sealed class Qualification(val name: String) {
    object Unqualified : Qualification("")
    data class Qualified(val qualifier: Qualifier) : Qualification(qualifier.name)
}

interface Implementation {
    val qualifiers: List<Qualifier>
    val interfaces: List<Interface>
}

interface Interface
interface Qualifier {
    val name: String
}

internal object Utils {
    @JvmStatic
    fun module(environment: RoundEnvironment) =
        moduleBuilder()
            .add(environment.bindings())
            .toFile()

    @Suppress("RedundantLambdaArrow")
    private fun TypeSpec.Builder.add(functions: Iterable<FunSpec>) = also { _ ->
        functions.forEach { addFunction(it) }
    }

    private fun RoundEnvironment.bindings() =
        implementations()
            .flatMap { it.combineWithTypes() }
            .flatMap { it.combineWithQualifiers() }
            .map { it.toFunction() }


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

    data class KPQualifier(val className: ClassName) : Qualifier {

        override val name
            get() = className.simpleName()
    }

    data class KPInterface(val type: TypeMirror) : Interface

    data class KPImplementation(val element: Element) : Implementation {
        override val interfaces: List<Interface>
            get() = (element as TypeElement).interfaces.map { KPInterface(it) }
        override val qualifiers
            get() = element.getAnnotation(BindInterface::class.java)
                .qualifiers.map { it.className() }
                .map { KPQualifier(it) }
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

    private fun String.className() = try {
        ClassName.bestGuess(this)
    } catch (e: Exception) {
        throw Exception("${this} is invalid class name.", e)
    }

}
