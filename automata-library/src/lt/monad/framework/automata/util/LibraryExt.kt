/**
 * LibraryExt
 * Extension functions for RuneMate dependency.
 */
@file:Suppress("FunctionName")
package lt.monad.framework.automata.util

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.entities.Actor
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.local.hud.interfaces.Openable
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.osrs.local.hud.interfaces.Magic
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

// TODO: Refactor code.
/* ------------------------------ ------------------------------ */
// RuneMate logging functions.
/* ------------------------------ ------------------------------ */

fun DEBUG(message: String): Unit = Environment.getLogger().run { debug(message) }
fun ERROR(message: String): Unit = Environment.getLogger().run { severe(message) }
fun EXIT(message: String = "Bot terminated."): Unit = Environment.getBot().run {
    ERROR(message)
    stop(message)
}

/* ------------------------------ ------------------------------ */
// Bot timing control.
/* ------------------------------ ------------------------------ */

class DelayConfiguration
{
    lateinit var required: Deferred<Boolean>
    var reset: Deferred<Boolean> = { false }
    var interval = 600 to 1200
}

inline fun DELAY_UNTIL(configuration: DelayConfiguration.() -> Unit) {
    val delay = DelayConfiguration().apply(configuration)
    Execution.delayUntil(delay.required, delay.reset, delay.interval.first, delay.interval.second)
}

inline fun <T: Any> DELAY_UNTIL(receiver: T?, configuration: DelayConfiguration.() -> Unit): Unit = receiver?.let {
    DELAY_UNTIL(configuration)
} ?: Unit


inline fun DELAY_UNTIL(receiver: Boolean, configuration: DelayConfiguration.() -> Unit) {
    if (receiver) {
        DELAY_UNTIL(configuration)
    }
}

/* ------------------------------ ------------------------------ */
// Locatables, areas...
/* ------------------------------ ------------------------------ */

fun Locatable.nearBy(point: Locatable, threshold: Int): Boolean {
    return distanceTo(point) <= threshold
}

fun Locatable.nearBy(): Boolean {
    return nearBy(Players.getLocal(), 11)
}

/* ------------------------------ ------------------------------ */
// Misc.
/* ------------------------------ ------------------------------ */

/**
 * Create RegEx pattern object for item with charges/usages count
 * in it's name.
 *
 * Ex. Ring of dueling -> matches Ring of dueling(*)
 */
fun numerical_pattern(named: String): Pattern {
    return Pattern.compile("$named\\([\\d]\\)")
}

/**
 * Check if spell can be casted.
 */
fun Magic.Lunar.available(): Boolean {
    return this.spriteIdWhenAvailable == this.component.spriteId
}

/**
 * Interface window switch only when needed.
 */
fun Openable.switch(): Boolean = when(this.isOpen) {
    false -> { open() }
    else -> { true }
}

/* ------------------------------ ------------------------------ */
// Attribute extension.
/* ------------------------------ ------------------------------ */

val SpriteItem.named: String
    get() = definition.name

val Npc.named: String
    get() = definition.name