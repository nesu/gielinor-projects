package lt.monad.framework.automata.behaviour.dynamic

import lt.monad.framework.automata.behaviour.Node

abstract class Leaf : Node() {
    abstract fun call()
}