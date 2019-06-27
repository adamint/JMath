package com.adamratzman.math.rules.functions

import com.adamratzman.math.rules.Precedence
import java.math.BigDecimal
import java.math.MathContext

internal val unaryPlus = object : UnaryOperator("+", Precedence.unaryBasePrecedence) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return parameters[0]
    }
}

internal val unaryMinus = object : UnaryOperator("-", Precedence.unaryBasePrecedence) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return parameters[0].negate(context)
    }

}

fun getUnaryOperators() = listOf(
    unaryPlus,
    unaryMinus
)