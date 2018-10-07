package lt.monad.framework.automata.overlay.infographics

import com.runemate.game.api.hybrid.input.Mouse
import java.awt.Color
import java.awt.Graphics2D

/**
 * Renders mouse cursor and its' trail.
 */
class MouseOverlay
{
    private var trailing = MouseTrailOverlay()

    fun render(graphics: Graphics2D)
    {
        val x = Mouse.getPosition().getX().toInt()
        val y = Mouse.getPosition().getY().toInt()

        // Drawing mouse cross
        graphics.paint = Color.RED
        graphics.drawLine(x - 5, y + 5, x + 5, y - 5)
        graphics.drawLine(x + 5, y + 5, x - 5, y - 5)

        // Drawing mouse trail
        trailing.render(graphics)
    }
}