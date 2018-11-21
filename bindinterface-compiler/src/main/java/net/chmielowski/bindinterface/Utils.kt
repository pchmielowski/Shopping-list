package net.chmielowski.bindinterface

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import dagger.Binds

import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror

internal object Utils {
    fun function(typeElement: TypeElement, type: TypeMirror, qualifier: String?): FunSpec {
        val typeName = ClassName.bestGuess(type.toString())
        val qualifierName = qualifier?.let { ClassName.bestGuess(it) }
        return FunSpec.builder(functionName(typeName, qualifierName))
            .addAnnotation(Binds::class.java)
            .also { it.addQualifierAnnotation(qualifierName) }
            .addModifiers(KModifier.ABSTRACT)
            .addParameter("impl", ClassName.bestGuess(typeElement.qualifiedName.toString()))
            .returns(typeName)
            .build()
    }

    private fun FunSpec.Builder.addQualifierAnnotation(qualifierName: ClassName?) {
        qualifierName?.let { addAnnotation(it) }
    }

    private fun functionName(typeName: ClassName, qualifierName: ClassName?) =
        "bind${typeName.simpleName()}${qualifierName?.simpleName() ?: ""}"
}
