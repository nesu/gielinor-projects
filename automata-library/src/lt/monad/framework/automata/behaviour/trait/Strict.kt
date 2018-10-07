package lt.monad.framework.automata.behaviour.trait

/**
 * Trait [Strict] similarly like [Assured] validates specific condition,
 * but unlike [Assured], [Strict] halts bot process forcibly if some condition
 * was not satisfied.
 *
 * [Strict] should be used carefully, because it does not check if player
 * is in danger.
 */
interface Strict {
    fun requirement(): Boolean
}