package com.adamratzman.math.rules.functions

import com.adamratzman.math.rules.Associativity
import com.adamratzman.math.rules.FunctionToken
import com.adamratzman.math.rules.OperatorToken
import com.adamratzman.math.utils.functionPackagePath
import org.reflections.Reflections
import java.lang.reflect.Modifier
import java.math.BigDecimal

abstract class MathFunction(
    token: String,
    val type: FunctionType,
    maxParameterAmount: Int,
    minParameterAmount: Int = maxParameterAmount,
    val caseSensitive: Boolean = false,
    val aliases: List<String> = listOf()
) : FunctionToken(token, maxParameterAmount, minParameterAmount) {
    fun matches(candidate: String): Boolean = (aliases + token).any { it.equals(candidate, !caseSensitive) }
}

enum class FunctionType {
    TRIGONOMETRIC, ALGEBRAIC, COMPARISON, CONVERSION, CALCULUS, RANDOM
}

abstract class UnaryOperator(
    token: String,
    precedence: Int,
    associativity: Associativity = Associativity.RIGHT_ASSOCIATIVE
) : OperatorToken(token, precedence, 1, 1, associativity) {
    override fun toString() = "UnaryFunction($token)"
}

fun getFunctions(): List<MathFunction> = Reflections(functionPackagePath)
    .getSubTypesOf(MathFunction::class.java).filterNot { Modifier.isAbstract(it.modifiers) }
    .map { it.constructors[0].newInstance() as MathFunction }