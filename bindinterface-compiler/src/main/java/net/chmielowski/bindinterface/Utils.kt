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

    private fun TypeSpec.Builder.add(functions: Iterable<FunSpec>) = also { _ ->
        functions.forEach { addFunction(it) }
    }

    private fun RoundEnvironment.bindings() =
        implementations()
            .flatMap { it.combineWithTypes() }
            .flatMap { it.combineWithQualifiers() }
            .map { it.toFunction() }

    private fun Pair<TypeElement, TypeMirror>.combineWithQualifiers() =
        let { (implementation, type) ->
            implementation.qualifiers().let { qualifiers ->
                if (qualifiers.isEmpty()) {
                    listOf(implementation to type to null)
                } else {
                    qualifiers
                        .map { qualifier -> implementation to type to qualifier }
                }
            }
        }

    private infix fun <A, B, C> Pair<A, B>.to(that: C) = Triple(first, second, that)


    private fun Triple<TypeElement, TypeMirror, ClassName?>.toFunction() =
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

    private fun TypeMirror.bindingFunction(typeElement: TypeElement, qualifier: ClassName?): FunSpec {
        val typeName = toString().className()
        return FunSpec.builder(functionName(typeName, qualifier))
            .addAnnotation(Binds::class.java)
            .also { it.addQualifierAnnotation(qualifier) }
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

    private fun FunSpec.Builder.addQualifierAnnotation(qualifierName: ClassName?) {
        qualifierName?.let { addAnnotation(it) }
    }

    private fun functionName(typeName: ClassName, qualifierName: ClassName?) =
        "bind${typeName.simpleName()}${qualifierName?.simpleName() ?: ""}"
}
