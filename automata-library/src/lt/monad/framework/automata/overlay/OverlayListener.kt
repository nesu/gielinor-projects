package lt.monad.framework.automata.overlay

import java.awt.Graphics2D

interface OverlayListener {
    fun render(graphics: Graphics2D)
}