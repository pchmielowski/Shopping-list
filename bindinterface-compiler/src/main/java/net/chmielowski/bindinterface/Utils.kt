package net.chmielowski.bindinterface

import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.type.TypeMirror


sealed class Qualification(val name: String) {
    object Unqualified : Qualification("")
    data class Qualified(val qualifier: Qualifier) : Qualification(qualifier.name)
}

fun Pair<Implementation, Interface>.combineWithQualifiers() =
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


fun Implementation.combineWithTypes() =
    interfaces.map { type -> this to type }

fun RoundEnvironment.implementations() =
    getElementsAnnotatedWith(BindInterface::class.java)
        .map { KPImplementation(it) }


abstract class Codebase(val environment: RoundEnvironment) {
    abstract fun bindings(): List<Binding>
}

typealias Binding = Triple<Implementation, Interface, Qualification>


abstract class Interface(val type: TypeMirror)

interface Qualifier {
    val name: String
}


abstract class Implementation(val element: Element) {
    abstract val qualifiers: List<Qualifier>
    abstract val interfaces: List<Interface>
}
