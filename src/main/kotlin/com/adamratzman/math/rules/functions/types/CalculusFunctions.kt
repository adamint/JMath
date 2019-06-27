package com.adamratzman.math.rules.functions.types

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import java.math.BigDecimal
import java.math.MathContext

class Ln : MathFunction("ln", FunctionType.CALCULUS,1) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return BigDecimalMath.log(parameters[0], context)
    }
}