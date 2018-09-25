package lt.monad.framework.automata.behaviour.dynamic

import lt.monad.framework.automata.behaviour.Node
import lt.monad.framework.automata.behaviour.trait.Diverting

abstract class Divert : Node(), Diverting {
    abstract fun next(): Node
    final override fun forward(): Node {
        return next()
    }
}