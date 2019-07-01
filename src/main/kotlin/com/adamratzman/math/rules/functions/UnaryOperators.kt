package com.adamratzman.math.rules.functions

import com.adamratzman.math.parser.ExpressionEvaluator
import com.adamratzman.math.rules.Precedence
import java.math.BigDecimal

internal val unaryPlus = object : UnaryOperator("+", Precedence.unaryBasePrecedence) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return parameters[0]
    }
}

internal val unaryMinus = object : UnaryOperator("-", Precedence.unaryBasePrecedence) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return parameters[0].negate(evaluator.context)
    }

}

fun getUnaryOperators() = listOf(
    unaryPlus,
    unaryMinus
)