package com.github.hitsound.daonplugin.effect

class ConcurrentEffect(private val unorderedEffects: Collection<Effect<*>>): Effect<ConcurrentEffect> {
    override fun invoke() {
        unorderedEffects.parallelStream().parallel().forEach(Effect<*>::invoke)
    }
}