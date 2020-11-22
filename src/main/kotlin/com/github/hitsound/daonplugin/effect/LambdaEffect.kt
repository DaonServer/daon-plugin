package com.github.hitsound.daonplugin.effect

class LambdaEffect<T>(val value: T, val function: (T) -> Unit) : Effect<LambdaEffect<T>> {
    override fun invoke() {
        function(value)
    }
}