package com.adamratzman.math.rules

import java.math.BigDecimal
import java.math.MathContext

abstract class Token(
    val token: String,
    val precedence: Int,
    private val name: String,
    val associativity: Associativity = Associativity.LEFT_ASSOCIATIVE
) {
    override fun toString() = "$name($token)"
}

enum class Associativity {
    LEFT_ASSOCIATIVE, RIGHT_ASSOCIATIVE, NON_ASSOCIATIVE
}

abstract class StackableToken(
    token: String,
    precedence: Int,
    associativity: Associativity = Associativity.LEFT_ASSOCIATIVE
) : Token(token, precedence, token, associativity) {
    override fun toString() = "MathFunction($token)"
}

abstract class OperatorToken(
    token: String,
    precedence: Int,
    val maxParameterAmount: Int = 2,
    val minParameterAmount: Int = maxParameterAmount,
    associativity: Associativity = Associativity.LEFT_ASSOCIATIVE
) : StackableToken(token, precedence, associativity) {
    abstract fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal
}

abstract class FunctionToken(
    token: String,
    val maxParameterAmount: Int,
    val minParameterAmount: Int = maxParameterAmount
) : StackableToken(token, Precedence.functionPrecedence) {
    abstract fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal
}

abstract class UnstackableToken(token: String, name: String) : Token(
    token, Precedence.nonStackablePrecedence, name,
    Associativity.LEFT_ASSOCIATIVE
)

class VariableToken(name: String) : UnstackableToken(name, "Variable")
class NumberToken(name: String) : UnstackableToken(name, "Number")

class Precedence {
    companion object {
        val nonStackablePrecedence = -1

        val functionPrecedence = Int.MAX_VALUE
        val parenthesisPrecedence = functionPrecedence - 1

        val unaryBasePrecedence = parenthesisPrecedence - 15
    }
}


abstract class MathValue {
    abstract fun add(mathValue: MathValue): MathValue
    abstract fun subtract(mathValue: MathValue): MathValue
    abstract fun multiply(mathValue: MathValue): MathValue
    abstract fun divide(mathValue: MathValue): MathValue

    abstract fun exponent(mathValue: MathValue): MathValue

}

//class Fraction(val numerator: BigDecimal, val denominator: BigDecimal) : MathValue()
class Number(val number: BigDecimal)
class Constant()