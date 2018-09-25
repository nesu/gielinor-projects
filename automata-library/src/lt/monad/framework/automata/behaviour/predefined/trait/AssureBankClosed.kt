package lt.monad.framework.automata.behaviour.predefined.trait

import lt.monad.framework.automata.behaviour.trait.Assured
import lt.monad.framework.automata.util.DELAY_UNTIL
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank

interface AssureBankClosed : Assured
{
    override fun invoke(): Boolean
    {
        DELAY_UNTIL(Bank.close(true)) {
            required = { !Bank.isOpen() }
            reset = { Bank.isOpen() }
        }

        return !Bank.isOpen()
    }
}