package com.adamratzman.math.rules.functions

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.parser.ExpressionEvaluator
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
    vararg val parameters: FunctionOverloadDescription = arrayOf(FunctionOverloadDescription("number")),
    val caseSensitive: Boolean = false,
    val aliases: List<String> = listOf()
) : FunctionToken(token, maxParameterAmount, minParameterAmount) {

    init {
        if (parameters.isEmpty()) throw IllegalArgumentException("Parameter description for $this must not be empty!")
    }

    fun matches(candidate: String): Boolean = (aliases + token).any { it.equals(candidate, !caseSensitive) }

    companion object {
        const val MAX_PARAMETER_SIZE: Int = 128
    }
}

abstract class TrigFunction(
    token: String,
    maxParameterAmount: Int,
    minParameterAmount: Int = maxParameterAmount,
    vararg parameters: FunctionOverloadDescription = arrayOf(FunctionOverloadDescription("number")),
    caseSensitive: Boolean = false,
    aliases: List<String> = listOf()
) : MathFunction(
    token,
    FunctionType.TRIGONOMETRIC,
    maxParameterAmount,
    minParameterAmount,
    *parameters,
    caseSensitive = caseSensitive,
    aliases = aliases
) {
    abstract fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal

    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return if (evaluator.useRadians) computeRadians(parameters, evaluator)
        else computeRadians(parameters.map {
            it.times(BigDecimalMath.pi(evaluator.context)).divide(180.toBigDecimal(), evaluator.context)
        }, evaluator)
    }
}

enum class FunctionType {
    TRIGONOMETRIC, ALGEBRAIC, COMPARISON, CONVERSION, CALCULUS, RANDOM
}

class FunctionOverloadDescription(vararg parametersVararg: String, val description: String? = null) {
    val paramString: String = parametersVararg.joinToString(", ")

    override fun toString(): String = description?.let { "$paramString | $it" } ?: paramString
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