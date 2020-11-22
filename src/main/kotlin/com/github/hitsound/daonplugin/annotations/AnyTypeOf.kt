package com.github.hitsound.daonplugin.annotations

import kotlin.reflect.KClass

@Retention
@Target(AnnotationTarget.TYPE_PARAMETER)
annotation class AnyTypeOf(vararg val classes: KClass<*>) {
}