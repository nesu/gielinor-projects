package lt.monad.framework.automata.behaviour.predefined.trait

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows
import lt.monad.framework.automata.behaviour.trait.Assured
import lt.monad.framework.automata.util.DELAY_UNTIL

/**
 * Assurance trait which opens inventory interface
 * window if one is not present.
 */
interface AssureEquipmentInterfaceVisible : Assured
{
    override fun invoke(): Boolean
    {
        DELAY_UNTIL(InterfaceWindows.getEquipment().open()) {
            required = { InterfaceWindows.getEquipment().isOpen }
        }

        return InterfaceWindows.getEquipment().isOpen
    }
}