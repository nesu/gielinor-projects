package lt.monad.framework.automata.overlay.infographics

import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.script.framework.listeners.SkillListener
import com.runemate.game.api.script.framework.listeners.events.SkillEvent
import lt.monad.framework.automata.util.DEBUG

class SkillOverlay(private val overlays: RectangleOverlay, vararg skills: Skill) : SkillListener
{
    private val experiences = mutableMapOf<String, Int>()
    private val levels = mutableMapOf<String, Int>()

    init {
        create(*skills)
    }

    private fun create(vararg skills: Skill) {
        if (skills.isEmpty())
            return

        skills.forEach {
            experiences[it.name] = 0
            levels[it.name] = 0

            overlays.create {
                label = { label(it) }
            }
        }
    }

    private fun label(skill: Skill): String {
        return "${skill.name} (+${experiences[skill.name]} [+${levels[skill.name]} lv.])"
    }

    override fun onExperienceGained(event: SkillEvent?) = event?.let {
        val replace = experiences[it.skill.name] ?: 0
        experiences[it.skill.name] = replace + it.change
    } ?: Unit

    override fun onLevelUp(event: SkillEvent?) = event?.let {
        val replace = levels[it.skill.name] ?: 0
        levels[it.skill.name] = replace + it.change
    } ?: Unit
}