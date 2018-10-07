package lt.monad.framework.automata.behaviour.dynamic

import lt.monad.framework.automata.behaviour.Node

/**
 * [Node] object which is used for final actions without continuation.
 */
abstract class Leaf : Node() {
    abstract fun call()
}