package com.github.hitsound.daonplugin.extension

import com.github.hitsound.daonplugin.annotations.InferRightHand

inline fun <E, V> E.pipe(f: (E) -> V): V {
    return run(f)
}

@Suppress("UNCHECKED_CAST")
fun <@InferRightHand X, R> R.cast(): X {
    return this as X
}
