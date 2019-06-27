package com.adamratzman.math.rules.constants

import java.math.BigDecimal
import java.math.MathContext

abstract class Constant(val name: String, val caseSensitive: Boolean = false) {
    abstract fun generate(mathContext: MathContext): BigDecimal
}

class TrivialConstant(name: String, val value: (MathContext) -> BigDecimal, caseSensitive: Boolean = false)
    : Constant(name, caseSensitive) {
    override fun generate(mathContext: MathContext) = value(mathContext)
}