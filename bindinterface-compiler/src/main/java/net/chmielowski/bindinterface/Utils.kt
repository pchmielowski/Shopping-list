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

    sealed class Qualification(val name: String) {
        object Unqualified : Qualification("")
        data class Qualified(val qualifier: ClassName) : Qualification(qualifier.simpleName())
    }

    private fun Pair<TypeElement, TypeMirror>.combineWithQualifiers() =
        let { (implementation, type) ->
            fun combineWith(qualification: Qualification) = implementation to type to qualification
            implementation.qualifiers().let { qualifiers ->
                if (qualifiers.isEmpty()) {
                    listOf(combineWith(Qualification.Unqualified))
                } else {
                    qualifiers.map { combineWith(Qualification.Qualified(it)) }
                }
            }
        }


    private infix fun <A, B, C> Pair<A, B>.to(that: C) = Triple(first, second, that)


    private fun Triple<TypeElement, TypeMirror, Qualification>.toFunction() =
        let { (implementation, type, qualifier) ->
            type.bindingFunction(implementation, qualifier)

        }

    private fun TypeElement.combineWithTypes() =
        interfaces.map { type -> this to type }

    private fun RoundEnvironment.implementations() =
        getElementsAnnotatedWith(BindInterface::class.java)
            .map { it as TypeElement }

    private fun TypeSpec.Builder.toFile() =
        FileSpec.builder("net.chmielowski.bindinterface", "GeneratedModule")
            .addType(build())
            .build()

    private fun moduleBuilder(): TypeSpec.Builder {
        return TypeSpec.classBuilder("InterfaceBindingsModule")
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(Module::class.java)
    }

    private fun Element.qualifiers() = getAnnotation(BindInterface::class.java)
        .qualifiers.map { it.className() }

    private fun TypeMirror.bindingFunction(typeElement: TypeElement, qualification: Qualification): FunSpec {
        val typeName = toString().className()
        return FunSpec.builder("bind${typeName.simpleName()}${qualification.name}")
            .addAnnotation(Binds::class.java)
            .also {
                if (qualification is Qualification.Qualified) it.addAnnotation(qualification.qualifier)
            }
            .addModifiers(KModifier.ABSTRACT)
            .addParameter("impl", typeElement.qualifiedName.toString().className())
            .returns(typeName)
            .build()
    }

    private fun String.className() = try {
        ClassName.bestGuess(this)
    } catch (e: Exception) {
        throw Exception("Invalid class name.", e)
    }

}
