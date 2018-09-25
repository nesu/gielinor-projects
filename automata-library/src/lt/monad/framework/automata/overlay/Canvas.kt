package lt.monad.framework.automata.overlay

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent

internal class Canvas(private val overlay: Overlay) : JComponent()
{
    init {
        isOpaque = false
        isFocusable = false
        background = Color(0, 0, 0, 0)
    }

    override fun paintComponent(graphics: Graphics?) {
        super.paintComponent(graphics)
        graphics?.let {
            it.color = Color( 170, 1, 20, 240)
            it.drawRect(x + 2, y + 2, width - 5, height - 5)
            it.drawRect(x + 5, y + 5, width - 11, height - 11)

            val g2d = graphics.create() as Graphics2D
            try {
                overlay.render(g2d)
            } finally {
                g2d.dispose()
            }
        }
    }
}