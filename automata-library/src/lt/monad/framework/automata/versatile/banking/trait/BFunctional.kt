package lt.monad.framework.automata.versatile.banking.trait

import lt.monad.framework.automata.behaviour.Node
import lt.monad.framework.automata.behaviour.dynamic.Branch
import lt.monad.framework.automata.behaviour.dynamic.Leaf
import lt.monad.framework.automata.util.DEBUG
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank

/**
 * Interface trait for bank process.
 * Trait lets to specify following action after
 * banking action was successfully completed.
 *
 * Before executing bank window must be closed.
 */
interface BFunctional {
    /**
     * Node to branch or leaf which will be
     * executed after successful bank action.
     */
    fun next(): Node

    /**
     * Function which will indicate when functional
     * action was completed.
     */
    fun completed(): Boolean

    /**
     * Evaluation and execution for functional node.
     */
    fun act(): Node = object : Branch()
    {
        override fun eval(): Boolean {
            return Bank.isOpen()
        }

        override fun success() = object  : Leaf()
        {
            override fun call() {
                DEBUG("[BFunctional] Closing bank.")
                Bank.close()
            }
        }

        override fun failure(): Node {
            return next()
        }
    }
}