package lt.monad.framework.automata.behaviour.dynamic

import lt.monad.framework.automata.behaviour.Node
import lt.monad.framework.automata.behaviour.trait.Diverting
import lt.monad.framework.automata.util.DEBUG

abstract class Branch : Node(), Diverting {
    abstract fun eval(): Boolean
    abstract fun success(): Node
    abstract fun failure(): Node
    final override fun forward(): Node {
        DEBUG("Validating ${this.javaClass.name}. Evaluation result: ${eval()}.")
        return if (eval()) success() else failure()
    }
}