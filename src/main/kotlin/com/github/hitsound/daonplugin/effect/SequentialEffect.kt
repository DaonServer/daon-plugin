package com.github.hitsound.daonplugin.effect

class SequentialEffect(private val orderedEffects: List<Effect<*>>): Effect<SequentialEffect> {
    override fun invoke() {
        orderedEffects.forEach(Effect<*>::invoke)
    }
}