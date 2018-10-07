package lt.monad.framework.automata.entities

import com.runemate.game.api.hybrid.entities.Actor
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.region.Players
import java.lang.Exception

object LocalPlayer
{
    /**
     * Retrieve local player.
     * @return [Player]
     */
    fun get(): Player {
        return Players.getLocal() ?: throw Exception("Could not retrieve player data object.")
    }

    /**
     * Returns true if player is not idling.
     * @return <code>true</code if player is animating
     *         <code>false</code> otherwise.
     */
    fun animating(): Boolean {
        return get().animationId != -1
    }

    /**
     * Returns true if player is moving.
     * @return <code>true</code if player is moving
     *         <code>false</code> otherwise.
     */
    fun moving(): Boolean {
        return get().isMoving
    }

    /**
     * Returns true if player is visible.
     * @return <code>true</code if player is visible
     *         <code>false</code> otherwise.
     */
    fun visible(): Boolean {
        return get().isVisible
    }

    /**
     * Returns true if player currently targeting [Actor].
     * @return <code>true</code if player is targeting [Actor]
     *         <code>false</code> otherwise.
     */
    fun targeting(): Boolean {
        return get().target != null
    }
}