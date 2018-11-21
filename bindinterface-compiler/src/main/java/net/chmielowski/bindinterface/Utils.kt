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
        return moduleBuilder()
            .also { builder ->
                environment.functions().forEach { f ->
                    builder.addFunction(f)
                }
            }
            .let { file(it) }
    }

    private fun RoundEnvironment.functions(): MutableList<FunSpec> {
        val list = mutableListOf<FunSpec>()
        implementations(this).forEach { implementation ->
            val qualifiers = implementation.qualifiers()
            implementation.interfaces.forEach { type ->
                type.bindings(qualifiers, implementation).forEach { function ->
                    list.add(function)
                }
            }
        }
        return list
    }

    private fun TypeMirror.bindings(qualifiers: List<ClassName>, implementation: TypeElement) =
        if (qualifiers.isEmpty()) {
            listOf(unqualifiedBindingFunction(implementation))
        } else {
            qualifiers
                .map { qualifier -> bindingFunction(implementation, qualifier) }
        }

    private fun implementations(roundEnv: RoundEnvironment) =
        roundEnv.getElementsAnnotatedWith(BindInterface::class.java)
            .map { it as TypeElement }

    private fun file(builder: TypeSpec.Builder) =
        FileSpec.builder("net.chmielowski.bindinterface", "GeneratedModule")
            .addType(builder.build())
            .build()

    private fun moduleBuilder(): TypeSpec.Builder {
        return TypeSpec.classBuilder("InterfaceBindingsModule")
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(Module::class.java)
    }

    private fun Element.qualifiers() = getAnnotation(BindInterface::class.java)
        .qualifiers.map { it.className() }

    private fun TypeMirror.unqualifiedBindingFunction(typeElement: TypeElement) =
        bindingFunction(typeElement, null)

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
