package lt.monad.framework.automata.behaviour.predefined

import lt.monad.framework.automata.behaviour.dynamic.Leaf

class Idle : Leaf() {
    override fun call() {}
    companion object {
        val self = Idle()
    }
}