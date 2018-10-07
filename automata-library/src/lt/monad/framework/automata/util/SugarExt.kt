/**
 * SugarExt
 */
package lt.monad.framework.automata.util

/**
 * Type alias for deferred function.
 */
typealias Deferred<T> = () -> T

/**
 * Block will be executed only when [receiver] is not null.
 * @return [Unit]
 */
@Deprecated("Consider using let which is same shit but in different hand.", replaceWith = ReplaceWith("receiver?.let(block)"))
fun <T: Any> using(receiver: T?, block: (T) -> Unit): Unit? {
    return receiver?.let(block)
}