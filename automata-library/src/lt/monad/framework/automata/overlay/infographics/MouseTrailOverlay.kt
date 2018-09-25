package lt.monad.framework.automata.overlay.infographics

import com.runemate.game.api.hybrid.input.Mouse
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.util.*

internal class MouseTrailOverlay
{
    class TrailPoint(
            x: Int,
            y: Int,
            private val birth: Long = System.currentTimeMillis()) : Point(x ,y) {
        fun alive(): Boolean = lifetime() <= TRAILING_DELAY
        fun alpha(): Int {
            val alpha = 255 - Math.ceil((lifetime()/ FADE_OUT_VALUE).toDouble())

            if (alpha > 255)
                return 255
            else if(alpha < 0)
                return 0

            return alpha.toInt()
        }
        private fun lifetime(): Long = (System.currentTimeMillis() - birth)
    }

    private val points = LinkedList<TrailPoint>()

    fun render(graphics: Graphics2D)
    {
        points.removeIf {
            !it.alive()
        }

        val mx = Mouse.getPosition().x
        val my = Mouse.getPosition().y

        val point = TrailPoint(mx, my)
        graphics.drawLine(mx - 5, my + 5, mx + 5, my - 5)
        graphics.drawLine(mx + 5, my + 5, mx - 5, my - 5)

        if (points.isEmpty() || points.last != point) {
            points.add(point)
        }

        var previous: TrailPoint? = null
        for (it in points)
        {
            if (!it.alive()) {
                continue
            }

            if (previous != null) {
                graphics.color = Color(255, 0, 0, it.alpha())
                graphics.drawLine(it.x, it.y, previous.x, previous.y)
            }

            previous = it
        }
    }

    companion object {
        const val TRAILING_DELAY: Int = 550
        const val FADE_OUT_VALUE: Float = TRAILING_DELAY.toFloat() / 255f
    }
}