package com.adamratzman.math.rules.constants

import ch.obermuhlner.math.big.BigDecimalMath

val pi = TrivialConstant("pi", { tokenizer -> BigDecimalMath.pi(tokenizer.mathContext)})

val e = TrivialConstant("e", { mathContext -> BigDecimalMath.e(mathContext.mathContext) })

fun getTrivialConstants() = listOf(
    pi,
    e
)

val minInt = TypeConstant("MIN_INT", Int.MIN_VALUE.toBigDecimal())
val maxInt = TypeConstant("MAX_INT", Int.MAX_VALUE.toBigDecimal())

val minLong = TypeConstant("MIN_LONG", Long.MIN_VALUE.toBigDecimal())
val maxLong = TypeConstant("MAX_LONG", Long.MAX_VALUE.toBigDecimal())


fun getTypeConstants() = listOf(
    minInt,
    maxInt,
    minLong,
    maxLong
)