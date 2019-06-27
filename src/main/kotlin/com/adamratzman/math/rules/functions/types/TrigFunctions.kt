package com.adamratzman.math.rules.functions.types

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import java.math.BigDecimal
import java.math.MathContext

class SinFunction : MathFunction("sin", FunctionType.TRIGONOMETRIC,1) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        val first = parameters[0]

        return BigDecimalMath.sin(first, context)
    }
}