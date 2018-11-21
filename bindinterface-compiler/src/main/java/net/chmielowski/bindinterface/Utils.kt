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
            .also { it.addBindings(environment) }
            .toFile()

    private fun TypeSpec.Builder.addBindings(environment: RoundEnvironment) {
        environment
            .bindings()
            .forEach { addFunction(it) }
    }

    private fun RoundEnvironment.bindings() =
        implementations()
            .flatMap { it.combineWithTypes() }
            .flatMap { it.combineWithQualifiers() }
            .map { it.toFunction() }

    data class ImplementationTypeCombination(
        val implementation: TypeElement,
        val type: TypeMirror
    )

    data class ImplementationTypeQualifierCombination(
        val implementation: TypeElement,
        val type: TypeMirror,
        val qualifier: ClassName?
    )

    private fun ImplementationTypeCombination.combineWithQualifiers() =
        implementation.qualifiers().let { qualifiers ->
            if (qualifiers.isEmpty()) {
                listOf(ImplementationTypeQualifierCombination(implementation, type, null))
            } else {
                qualifiers
                    .map { qualifier -> ImplementationTypeQualifierCombination(implementation, type, qualifier) }
            }
        }

    private fun ImplementationTypeQualifierCombination.toFunction() =
        type.bindingFunction(implementation, qualifier)

    private fun TypeElement.combineWithTypes() =
        interfaces.map { type -> ImplementationTypeCombination(this, type) }

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
