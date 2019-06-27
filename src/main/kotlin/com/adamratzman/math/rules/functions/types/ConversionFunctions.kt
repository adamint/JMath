package com.adamratzman.math.rules.functions.types

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import java.math.BigDecimal
import java.math.MathContext

class ToDegree : MathFunction("deg", FunctionType.CONVERSION, 1, aliases = listOf("degrees", "d")) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return parameters[0].times(BigDecimal.valueOf(180)).divide(BigDecimalMath.pi(context), context)
    }
}

class ToRadians : MathFunction("rad", FunctionType.CONVERSION, 1, aliases = listOf("radians", "r")) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return parameters[0].times(BigDecimalMath.pi(context)).divide(BigDecimal.valueOf(180), context)
    }
}