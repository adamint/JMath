package com.adamratzman.math.parser

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.rules.*
import com.adamratzman.math.rules.functions.UnaryOperator
import com.adamratzman.math.rules.functions.functionMarker
import java.math.BigDecimal
import java.math.MathContext
import java.util.*

class ExpressionEvaluator(val programInput: String, val tokenizedProgram: List<Token>, val context: MathContext) {
    constructor(programInput: String, context: MathContext) : this(
        programInput,
        ExpressionTokenizer(context).tokenize(programInput),
        context
    )

    val variables = mutableMapOf<String, (ExpressionEvaluator) -> BigDecimal>()

    fun runPreEvaluationChecks(): List<Token> {
        val tokens = tokenizedProgram.toMutableList()

        variables.forEach { variable ->
            tokens.mapIndexedNotNull { i, token ->
                if (token is VariableToken && token.token == variable.key) i
                else null
            }.forEach { indexToChange ->
                tokens[indexToChange] = NumberToken(variable.value(this).toPlainString())
            }
        }

        if (tokens.any { it is VariableToken }) {
            throw IllegalArgumentException("Tokenized program $tokens contained variable token(s)")
        }

        return tokens
    }

    fun evaluate(): BigDecimal {
        val tokens = runPreEvaluationChecks()

        val stack = Stack<Any>()

        tokens.forEach { token ->
            if (token is UnstackableToken) {
                if (token is NumberToken) stack.push(BigDecimalMath.toBigDecimal(token.token))
                else throw IllegalArgumentException("Unstackable $token is not a number")
            } else {
                when (token) {
                    functionMarker -> stack.push(functionMarker)
                    is FunctionToken -> {
                        if (stack.size < token.minParameterAmount) {
                            throw IllegalArgumentException(
                                "Stack for operator $token doesn't contain the correct " +
                                        "amount of parameters (${token.minParameterAmount}..${token.maxParameterAmount})"
                            )
                        }

                        val stackIndexOfLastFunctionMarker =
                            stack.indexOfLast { it == functionMarker }

                        val arguments = mutableListOf<BigDecimal>()

                        stack.removeAt(stackIndexOfLastFunctionMarker)

                        while (stack.size > stackIndexOfLastFunctionMarker) {
                            val argument = stack.removeAt(stackIndexOfLastFunctionMarker) as? BigDecimal
                                ?: throw IllegalArgumentException("Stack argument $stack must be a big decimal")
                            arguments.add(argument)
                        }

                        if (arguments.size !in token.minParameterAmount..token.maxParameterAmount) {
                            throw IllegalArgumentException("Argument size for $token (${arguments.size}) different than allowed.")
                        }

                        stack.push(token.compute(arguments, context))
                    }
                    is OperatorToken -> {
                        if (stack.size < token.minParameterAmount) {
                            throw IllegalArgumentException(
                                "Stack $stack for operator $token doesn't contain the correct " +
                                        "amount of parameters (${token.minParameterAmount}..${token.maxParameterAmount})"
                            )
                        }


                        val arguments = mutableListOf<BigDecimal>()

                        while (arguments.size < token.maxParameterAmount && stack.isNotEmpty()) {
                            val peek = stack.peek()
                            val argument = stack.pop() as? BigDecimal
                                ?: throw IllegalArgumentException(
                                    "Stack argument $peek must be a big decimal - " +
                                            "Stack: $stack, Function: $token, Tokens: $tokens"
                                )
                            arguments.add(argument)
                        }

                        if (arguments.size !in token.minParameterAmount..token.maxParameterAmount) {
                            throw IllegalArgumentException(
                                "Stack for operator $token doesn't contain the correct " +
                                        "amount of parameters (${token.minParameterAmount}..${token.maxParameterAmount})"
                            )
                        }

                        arguments.reverse()

                        stack.push(token.compute(arguments, context))


                        /*val stackIndexOfLastFunctionMarker =
                            stack.indexOfLast { it == functionMarker }

                        val arguments = mutableListOf<BigDecimal>()

                        stack.removeAt(stackIndexOfLastFunctionMarker)

                        while (stack.size > stackIndexOfLastFunctionMarker) {
                            val argument = stack.removeAt(stackIndexOfLastFunctionMarker) as? BigDecimal
                                ?: throw IllegalArgumentException("Stack argument $stack must be a big decimal")
                            arguments.add(argument)
                        }

                        if (arguments.size !in token.minParameterAmount..token.maxParameterAmount) {
                            throw IllegalArgumentException("Argument size for $token (${arguments.size}) different than allowed.")
                        }

                        stack.push(token.compute(arguments, context))*/

                    }
                    else -> throw IllegalArgumentException("Stackable token $token not operator or function")
                }
            }
        }

        if (stack.size != 1) throw IllegalStateException("Stack size not 1 at end of evaluation: $stack")

        return stack[0] as BigDecimal
    }

    fun inputToString(strictRpnNotation: Boolean = true): String {
        return if (!strictRpnNotation) runPreEvaluationChecks().toString()
        else runPreEvaluationChecks().joinToString(" ") { if (it is UnaryOperator) "u${it.token}" else it.token }
    }
}