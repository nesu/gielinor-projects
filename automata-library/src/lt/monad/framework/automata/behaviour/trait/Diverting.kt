package lt.monad.framework.automata.behaviour.trait

import lt.monad.framework.automata.behaviour.Node

interface Diverting {
    fun forward(): Node
}