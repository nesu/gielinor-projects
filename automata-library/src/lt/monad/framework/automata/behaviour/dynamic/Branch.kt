package lt.monad.framework.automata.behaviour.dynamic

import lt.monad.framework.automata.behaviour.Node
import lt.monad.framework.automata.behaviour.trait.Diverting
import lt.monad.framework.automata.util.DEBUG

/**
 * Conditional [Node] which evaluates given condition and proceeds
 * accordingly of evaluation result.
 */
abstract class Branch : Node(), Diverting {
    abstract fun eval(): Boolean
    abstract fun success(): Node
    abstract fun failure(): Node
    final override fun forward(): Node {
        return if (eval()) success() else failure()
    }
}