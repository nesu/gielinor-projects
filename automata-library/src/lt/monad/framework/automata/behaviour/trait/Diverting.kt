package lt.monad.framework.automata.behaviour.trait

import lt.monad.framework.automata.behaviour.Node

/**
 * Trait [Diverting] is used for classes that extend [Node] and are not
 * final, meaning that [Node] classes with trait [Diverting] has continuation.
 *
 * Continuation can be evaluated by overriding method [Diverting.forward].
 */
interface Diverting {
    fun forward(): Node
}