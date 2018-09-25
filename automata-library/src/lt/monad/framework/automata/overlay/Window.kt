package lt.monad.framework.automata.overlay

import com.sun.jna.Native
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.platform.win32.WinUser
import com.sun.jna.ptr.IntByReference
import lt.monad.framework.automata.util.DEBUG
import lt.monad.framework.automata.util.ERROR
import java.awt.Rectangle

object Window
{
    private val processes = mutableMapOf<Int, WinDef.HWND>()

    private val wndenumproc = WinUser.WNDENUMPROC { hwnd: WinDef.HWND?, _ ->
        val pointer = IntByReference()
        User32.INSTANCE.GetWindowThreadProcessId(hwnd, pointer)
        if (hwnd != null && validate(hwnd)) {
            processes[pointer.value] = hwnd
        }
        return@WNDENUMPROC true
    }

    fun get(pid: Int): WinDef.HWND? {
        User32.INSTANCE.EnumWindows(wndenumproc, null)
        return processes[pid]
    }

    fun rectangle(handle: WinDef.HWND): Rectangle? {
        val rectangle = WinDef.RECT()
        User32.INSTANCE.GetWindowRect(handle, rectangle)
        val fx = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CXFRAME)
        val fy = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CXFRAME)
        val my = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CYCAPTION)
        rectangle.left += fx
        rectangle.right -= fx
        rectangle.top += fy + my
        rectangle.bottom -= fy
        return rectangle.toRectangle()
    }

    private fun validate(hwnd: WinDef.HWND): Boolean {
        val title = title(hwnd)
        return title.isNotEmpty() && title.isNotBlank() && User32.INSTANCE.IsWindowVisible(hwnd)
    }

    fun title(hwnd: WinDef.HWND): String {
        val length = User32.INSTANCE.GetWindowTextLength(hwnd) + 1
        val buffer = CharArray(length)
        User32.INSTANCE.GetWindowText(hwnd, buffer, length)
        return Native.toString(buffer)
    }
}