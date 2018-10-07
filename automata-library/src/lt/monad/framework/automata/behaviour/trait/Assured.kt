package lt.monad.framework.automata.behaviour.trait

/**
 * Trait [Assured] is used when specific condition must be validated.
 * If validation fails, trait must execute required set of actions which
 * must result in trait's validation success.
 *
 * This trait is usually used for small interactions that does not require
 * complex conditioning, e.g. interface control.
 */
interface Assured {
    fun invoke(): Boolean
}