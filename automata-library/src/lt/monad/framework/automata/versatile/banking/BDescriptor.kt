package lt.monad.framework.automata.versatile.banking

import lt.monad.framework.automata.behaviour.Node
import lt.monad.framework.automata.behaviour.dynamic.Branch
import lt.monad.framework.automata.behaviour.dynamic.Leaf
import lt.monad.framework.automata.behaviour.predefined.Idle
import lt.monad.framework.automata.util.*
import lt.monad.framework.automata.versatile.banking.trait.BFunctional
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank

abstract class BDescriptor
{
    /**
     * Function for describing exact bank operation.
     */
    abstract fun operate()

    /**
     * Function for evaluating requirements.
     */
    abstract fun require(): Boolean

    /**
     * Function for validating operation outcome.
     */
    abstract fun validate(): Boolean

    /**
     * Outcome handler.
     */
    abstract fun callback(type: HandleType)

    /**
     * Type of bank process outcome.
     */
    enum class HandleType {
        REQUIREMENT_ASSESSMENT_FAILED, RESULT_ASSESSMENT_SUCCEED
    }

    /**
     * Get current object as functional trait.
     */
    private fun functional(): BFunctional {
        return this as? BFunctional ?:
                throw IllegalAccessException("Cannot cast banking descriptor as functional.")
    }

    /**
     * Get current object.
     */
    private fun get(): BDescriptor {
        return this
    }

    /**
     *
     */
    fun execute(): Node = object : Branch()
    {
        override fun eval(): Boolean {
            return get() is BFunctional
        }
        /**
         * Descriptor is functional.
         * Player must complete banking process and after
         * that close bank. Only then functional node will
         * be executed.
         */
        override fun success() = object : Branch()
        {
            override fun eval(): Boolean {
                return functional().completed()
            }
            /**
             * Functional node was not executed or failed.
             * Repeating until it's done.
             */
            override fun failure() = object : Branch()
            {
                override fun eval(): Boolean {
                    return get().validate()
                }

                override fun success(): Node {
                    return functional().act()
                }

                override fun failure(): Node {
                    return get().operational()
                }
            }
            /**
             * Functional node was completed.
             * Leaf should not be called too much.
             * TODO: Implement execution counter to ensure that behaviour tree is correct.
             */
            override fun success() = object : Leaf()
            {
                override fun call() {
                    DEBUG("[BDescriptor] Functional completed.")
                }
            }
        }
        /**
         * Descriptor is not functional.
         * Expand here if new traits should be implemented.
         *
         * But for now it's simple bank operation execution.
         */
        override fun failure(): Node
        {
            return object : Branch()
            {
                override fun eval(): Boolean {
                    return get().validate()
                }

                override fun success() = Idle.self
                override fun failure() = get().operational()
            }
        }
    }

    private fun operational() = object : Branch()
    {
        override fun eval(): Boolean {
            return Bank.isOpen()
        }

        override fun success() = object : Branch()
        {
            override fun eval(): Boolean {
                return get().require()
            }

            override fun success() = object : Leaf()
            {
                override fun call() {
                    DEBUG("Bank process begin.")
                    get().operate()
                }
            }

            override fun failure() = object : Leaf()
            {
                override fun call()
                {
                    // TODO: Implement fail-safe?
                    DEBUG("Requirement assessment failed for banking process. Calling abstract handler.")
                    get().callback(HandleType.REQUIREMENT_ASSESSMENT_FAILED)
                }
            }
        }

        override fun failure() = object : Leaf()
        {
            override fun call() {
                ERROR("Invalid banking descriptor usage. Before executing descriptor make sure that bank interface is visible.")
            }
        }
    }
}