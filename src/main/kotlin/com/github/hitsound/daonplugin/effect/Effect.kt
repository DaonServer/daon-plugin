package com.github.hitsound.daonplugin.effect

interface Effect<X : Effect<X>> {
    operator fun invoke()
}