package com.adamratzman.math.rules.constants

val pi = TrivialConstant("pi", { mathContext -> 3.14.toBigDecimal() })

val e = TrivialConstant("e", { mathContext -> 2.71.toBigDecimal() })

fun getTrivialConstants() = listOf(
    pi,
    e
)