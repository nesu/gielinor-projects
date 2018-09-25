package lt.monad.framework.automata.overlay

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

class Overlay : OverlayListener
{
    private val halt = AtomicBoolean(false)
    private val lock = Object()
    private val listeners = arrayListOf<OverlayListener>()

    private var frame: JFrame = JFrame()

    init {
        frame.extendedState = JFrame.MAXIMIZED_BOTH
        frame.isUndecorated = true
        frame.isAlwaysOnTop = true
        frame.isFocusable = false
        frame.focusableWindowState = false
        frame.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        frame.background = Color(0, 0, 0, 0)
        frame.add(Canvas(this), BorderLayout.CENTER)
        frame.setLocationRelativeTo(null)
    }

    fun visible(visible: Boolean = true) {
        frame.isVisible = visible
        frame.isAlwaysOnTop = visible
        frame.pack()
    }

    fun bounds(rectangle: Rectangle) = frame.run {
        if (bounds == rectangle)
            return@run

        setLocation(rectangle.x, rectangle.y)
        preferredSize = rectangle.size
        pack()
    }

    fun repaint() = frame.repaint()

    fun dispose() {
        if(halt.compareAndSet(false, true)) {
            SwingUtilities.invokeLater { frame.dispose() }
        }
    }

    fun submit(listener: OverlayListener) = synchronized(lock) {
        listeners.add(listener)
    }

    fun revoke(listener: OverlayListener) = synchronized(lock) {
        listeners.remove(listener)
    }

    override fun render(graphics: Graphics2D) = synchronized(lock) {
        listeners.forEach {
            it.render(graphics)
        }
    }
}