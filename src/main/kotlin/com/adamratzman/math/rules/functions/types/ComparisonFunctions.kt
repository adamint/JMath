package com.adamratzman.math.rules.functions.types

import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import java.math.BigDecimal
import java.math.MathContext

class Max : MathFunction("max", FunctionType.COMPARISON,2) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        val first = parameters[0]
        val second = parameters[1]

        return if (first > second) first else second
    }
}