package lt.monad.framework.automata.entities

import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.region.Players

object LocalPlayer
{
    fun get(): Player? {
        return Players.getLocal()
    }

    fun animating(): Boolean {
        return get()?.animationId != -1
    }

    fun moving(): Boolean {
        return get()?.isMoving ?: false
    }

    fun visible(): Boolean {
        return get()?.isVisible ?: false
    }
}