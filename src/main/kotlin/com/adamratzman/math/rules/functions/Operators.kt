package com.adamratzman.math.rules.functions

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.rules.Associativity
import com.adamratzman.math.rules.OperatorToken
import com.adamratzman.math.rules.Precedence
import java.math.BigDecimal
import java.math.MathContext

internal val plus = object : OperatorToken("+", 100) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return parameters[0].plus(parameters[1])
    }
}

internal val minus = object : OperatorToken("-", 100) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return parameters[0].minus(parameters[1])
    }
}

internal val times = object : OperatorToken("*", 101) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return parameters[0].times(parameters[1])
    }
}

internal val divide = object : OperatorToken("/", 101) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return parameters[0].divide(parameters[1], context)
    }
}

internal val modulo = object : OperatorToken("%", 101) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        val a = parameters[0]
        val b = parameters[1]

        return (a.remainder(b, context) + b).remainder(b, context)
    }
}

// we choose to implement pow as left associative
internal val power = object : OperatorToken("^", 102, associativity = Associativity.LEFT_ASSOCIATIVE) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return BigDecimalMath.pow(parameters[0], parameters[1], context)
    }
}

internal val leftParenthesis = object : OperatorToken("(", Precedence.parenthesisPrecedence, -1) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        throw NotImplementedError()
    }
}

internal val rightParenthesis = object : OperatorToken(")", Precedence.parenthesisPrecedence, -1) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        throw NotImplementedError()
    }
}

internal val functionMarker = object : OperatorToken("$", Precedence.parenthesisPrecedence, 0) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        throw NotImplementedError()
    }

}

fun getOperators() = listOf(
    plus,
    minus,
    times,
    divide,
    modulo,
    power
)