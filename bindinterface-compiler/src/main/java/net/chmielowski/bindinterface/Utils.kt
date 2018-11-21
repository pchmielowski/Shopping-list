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
    fun module(roundEnv: RoundEnvironment): FileSpec {
        val moduleBuilder = TypeSpec.classBuilder("InterfaceBindingsModule")
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(Module::class.java)
        for (element in roundEnv.getElementsAnnotatedWith(BindInterface::class.java)) {
            val typeElement = element as TypeElement
            val qualifiers = element.qualifiers()
            for (iface in typeElement.interfaces) {
                if (qualifiers.isEmpty()) {
                    moduleBuilder.addFunction(iface.unqualifiedBindingFunction(typeElement))
                } else {
                    for (qualifier in qualifiers) {
                        moduleBuilder.addFunction(iface.bindingFunction(typeElement, qualifier))
                    }
                }
            }
        }
        return FileSpec.builder("net.chmielowski.bindinterface", "GeneratedModule")
            .addType(moduleBuilder.build())
            .build()
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
