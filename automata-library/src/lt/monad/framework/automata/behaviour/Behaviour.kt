package lt.monad.framework.automata.behaviour

import lt.monad.framework.automata.behaviour.trait.Assured
import lt.monad.framework.automata.behaviour.trait.Diverting
import lt.monad.framework.automata.behaviour.trait.Strict
import com.runemate.game.api.script.framework.LoopingBot
import lt.monad.framework.automata.util.DEBUG
import lt.monad.framework.automata.util.EXIT

abstract class Behaviour : LoopingBot()
{
    abstract fun root(): Node?

    private var root: Node? = null

    final override fun onLoop()
    {
        if (root == null)
            root = root()

        var referenced = root ?:
                throw IllegalAccessException("Element in behaviour tree cannot be null.")

        while(referenced is Diverting) {
            referenced = referenced.forward()
            if (!evaluate_trait(referenced))
                return
        }

        if (referenced is lt.monad.framework.automata.behaviour.dynamic.Leaf) {
            if (!evaluate_trait(referenced))
                return

            referenced.call()
        }
    }

    private fun evaluate_trait(reference: Node): Boolean
    {
        if (!strict(reference)) {
            EXIT("Node failed to meet requirements. Process will not stop.")
            return false
        }

        if (!assured(reference)) {
            DEBUG("Node failed to meet assurance function. Retrying...")
            return false
        }

        return true
    }

    private fun strict(reference: Node): Boolean {
        return (reference as? Strict)?.requirement() ?: true
    }

    private fun assured(reference: Node): Boolean {
        return (reference as? Assured)?.invoke() ?: true
    }
}
