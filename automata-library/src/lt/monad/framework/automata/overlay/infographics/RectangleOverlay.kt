package lt.monad.framework.automata.overlay.infographics

import lt.monad.framework.automata.util.DEBUG
import lt.monad.framework.automata.util.Deferred
import java.awt.*

class RectangleOverlay
{
    private val rectangles = mutableListOf<RectangleObject>()

    fun next(): Point
    {
        if (rectangles.size == 0) {
            return Point(25, 25)
        }

        val y = rectangles.sumBy { it.rectangle?.height ?: 0 } + rectangles.size * 5
        return Point(25, 25 + y)
    }

    fun render(graphics: Graphics2D) {
        rectangles.forEach {
            it.render(graphics)
        }
    }

    fun create(body: RectangleObject.() -> Unit) {
        val rectangle = RectangleObject().apply(body)
        rectangles.add(rectangle)

        if (rectangle.at > -1 && rectangle.at < (rectangles.size - 1)) {
            rectangles[rectangle.at].rectangle?.let {
                val current = it.location
                it.location = next()
                rectangle.create(current)
            }

            return
        }

        rectangle.create(next())
    }

    class RectangleObject
    {
        lateinit var label: Deferred<String>
        var at = -1
        var rectangle: Rectangle? = null

        var dimension = Dimension(210, 30)
        var color = Color(136,14,79, 230)

        fun create(point: Point) {
            rectangle = Rectangle(point, dimension)
        }

        fun render(graphics: Graphics2D) {

            val rectangle = this.rectangle ?: return
            val sColor = graphics.color
            graphics.color = color
            graphics.fill(rectangle)

            val metrics = graphics.fontMetrics
            metrics.stringWidth(label()).let {
                val start_x = rectangle.x + ( (rectangle.width - it) / 2 )
                val start_y = rectangle.y + ((rectangle.height - metrics.height) / 2) + metrics.ascent
                graphics.color = Color.WHITE
                graphics.drawString(label(), start_x, start_y)
            }

            graphics.color = sColor
        }
    }
}