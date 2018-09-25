package lt.monad.framework.automata.overlay

import com.runemate.game.api.hybrid.Environment
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.platform.win32.WinUser
import lt.monad.framework.automata.util.DEBUG
import lt.monad.framework.automata.util.EXIT
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.Timer

class OverlayManager : ActionListener
{
    private val overlay: Overlay = Overlay()
    private val processId = Environment.getRuneScapeProcessId().toInt()
    private val timer = Timer(25, this)
    private var hwnd: WinDef.HWND? = null


    fun initialize() {
        if (timer.isRunning)
            return

        hwnd = Window.get(processId)

        timer.start()
        overlay.visible(true)
    }

    fun halt() {
        timer.stop()
        overlay.visible(false)
        overlay.dispose()
    }

    fun addListener(listener: OverlayListener) {
        overlay.submit(listener)
    }

    fun removeListener(listener: OverlayListener) {
        overlay.revoke(listener)
    }

    override fun actionPerformed(e: ActionEvent?) {
        val handle = hwnd ?: return
        val bounds = Window.rectangle(handle) ?: return
        val visible = AtomicBoolean(true)
        if (visible.compareAndSet(true, false)) {
            overlay.visible(true)
            overlay.bounds(bounds)
            overlay.repaint()
        } else {
            overlay.visible(false)
        }
    }
}