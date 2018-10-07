package lt.monad.framework.automata.behaviour.predefined.trait

import lt.monad.framework.automata.behaviour.trait.Assured
import lt.monad.framework.automata.util.DELAY_UNTIL
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows

/**
 * Assurance trait which opens magic interface
 * window if one is not present.
 */
interface AssureMagicInterfaceVisible : Assured
{
    override fun invoke(): Boolean
    {
        DELAY_UNTIL(InterfaceWindows.getMagic().open()) {
            required = { InterfaceWindows.getMagic().isOpen }
        }

        return InterfaceWindows.getMagic().isOpen
    }
}