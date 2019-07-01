package com.adamratzman.math.parser

import com.adamratzman.math.rules.*
import com.adamratzman.math.rules.constants.getSimpleConstants
import com.adamratzman.math.rules.functions.*
import java.math.MathContext
import java.util.*

class ExpressionTokenizer(var mathContext: MathContext, additionalMathFunctions: List<MathFunction>? = null) {
    private val operators = getOperators()
    private val unaryOperators = getUnaryOperators()

    private val internalFunctions = getFunctions()
    private val allMathFunctions: List<MathFunction> = internalFunctions + (additionalMathFunctions ?: listOf())

    private val constants = getSimpleConstants()

    private val globalTokens = operators + unaryOperators + allMathFunctions

    private fun isDigit(char: Char) = char in '0'..'9' || char == '.'
    private fun isLetter(char: Char) = char in 'a'..'z' || char in 'A'..'Z'
    private fun isIgnoredCharacter(char: Char) = char == ' '
    private fun isComma(char: Char) = char == ','
    private fun isDefinedToken(char: Char) = globalTokens.any { it.token == char.toString() }
    private fun getToken(char: Char) = globalTokens.first { it.token == char.toString() }
    private fun isLeftParenthesis(char: Char) = char == '('
    private fun isRightParenthesis(char: Char) = char == ')'

    fun tokenize(input: String): List<Token> {
        if (input.count { isRightParenthesis(it) } > input.count { isLeftParenthesis(it) }) {
            throw IllegalArgumentException("There are too many right parentheses in $input")
        }

        val tokens = mutableListOf<Token>()

        val operatorStack = Stack<Token>()

        val numberBuffer = mutableListOf<Char>()
        val letterBuffer = mutableListOf<Char>()

        input.forEachIndexed { i, char ->
            when {
                isLetter(char) -> {
                    if (numberBuffer.isEmpty()) letterBuffer.add(char)
                    else {
                        return tokenize(input.substring(0, i) + "*" + input.substring(i))
                    }
                }
                isDigit(char) -> {
                    if (letterBuffer.isNotEmpty()) letterBuffer.add(char)
                    else numberBuffer.add(char)
                }
                isComma(char) -> {
                    flushBuffers(numberBuffer, letterBuffer, tokens, operatorStack)
                }
                isDefinedToken(char) -> {
                    flushBuffers(numberBuffer, letterBuffer, tokens, operatorStack)

                    val token = checkIfUnary(getToken(char), operatorStack, input, i)

                    if (operatorStack.isEmpty()) operatorStack.push(token)
                    else if (operatorStack.peek().token == leftParenthesis.token
                        || operatorStack.peek() == functionMarker
                    ) {
                        operatorStack.push(token)
                    } else if (token.associativity == Associativity.LEFT_ASSOCIATIVE) {
                        if (token.precedence <= operatorStack.peek().precedence) {
                            tokens.add(operatorStack.pop())
                            operatorStack.push(token)
                        } else operatorStack.push(token)
                    } else if (token.associativity == Associativity.RIGHT_ASSOCIATIVE) {
                        if (token.precedence < operatorStack.peek().precedence) {
                            tokens.add(operatorStack.pop())
                            operatorStack.push(token)
                        } else operatorStack.push(token)
                    } else if (token.associativity == Associativity.NON_ASSOCIATIVE) {
                        operatorStack.push(token)
                    }

                    //   if (operatorStack.peek() == token) tokens.add(functionMarker)
                }
                isLeftParenthesis(char) -> {
                    flushBuffers(numberBuffer, letterBuffer, tokens, operatorStack)

                    if (tokens.lastOrNull() is NumberToken && input.substring(
                            i - tokens.last().token.length,
                            i
                        ) == tokens.last().token
                    ) {
                        // this will allow y(f(x)), where y is a number
                        return tokenize(input.substring(0, i) + "*" + input.substring(i))
                    }

                    if (operatorStack.isNotEmpty() && operatorStack.peek() is FunctionToken) tokens.add(functionMarker)
                    // place a parenthesis to indicate parameter start if start of function

                    operatorStack.push(leftParenthesis)
                }
                isRightParenthesis(char) -> {
                    flushBuffers(numberBuffer, letterBuffer, tokens, operatorStack)

                    while (operatorStack.isNotEmpty() && operatorStack.peek() != leftParenthesis) {
                        tokens.add(operatorStack.pop())
                    }

                    operatorStack.pop() // get rid of parenthesis

                    if (operatorStack.isNotEmpty() && operatorStack.peek() is FunctionToken) {
                        tokens.add(operatorStack.pop()) // function call
                    }
                }

                isIgnoredCharacter(char) -> {
                }
                else -> throw IllegalArgumentException("Unknown character $char at index $i")
            }
        }

        flushBuffers(numberBuffer, letterBuffer, tokens, operatorStack)

        while (operatorStack.isNotEmpty()) {
            tokens.add(operatorStack.pop())
        }

        tokens.removeAll { it == leftParenthesis }

        return tokens
    }

    private fun checkIfUnary(
        token: StackableToken,
        operatorStack: Stack<Token>,
        input: String,
        i: Int
    ): StackableToken {
        if (token !is OperatorToken) return token
        else {
            // check if this operator should be unary
            val adjustedStartIndex = i + 1 - token.token.length

            val isUnary = adjustedStartIndex == 0 ||
                    (operatorStack.isNotEmpty()
                            && input.substring(i - operatorStack.peek().token.length, i) == operatorStack.peek().token)

            return if (!isUnary) {
                if (token !is UnaryOperator) token
                else globalTokens.first { it !is UnaryOperator && it.token == token.token }
            } else {
                if (token is UnaryOperator) token
                else globalTokens.first { it is UnaryOperator && it.token == token.token }
            }
        }
    }

    private fun flushBuffers(
        numberBuffer: MutableList<Char>,
        letterBuffer: MutableList<Char>,
        tokens: MutableList<Token>,
        operatorStack: Stack<Token>
    ) {
        if (numberBuffer.isNotEmpty() && letterBuffer.isNotEmpty()) {
            throw IllegalArgumentException("Both number and letter buffers are not empty!")
        } else {
            if (numberBuffer.isNotEmpty()) tokens.add(NumberToken(numberBuffer.joinToString("")))

            if (letterBuffer.isNotEmpty()) {
                val string = letterBuffer.joinToString("")
                val foundFunction = allMathFunctions.find { it.matches(string) }

                when {
                    foundFunction != null -> operatorStack.push(foundFunction)
                    constants.find { it.name.equals(string, !it.caseSensitive) } != null -> tokens.add(
                        NumberToken(constants.first {
                            it.name.equals(
                                string,
                                !it.caseSensitive
                            )
                        }.generate(this).toPlainString())
                    )
                    else -> tokens.add(VariableToken(string))
                }
            }

            numberBuffer.clear()
            letterBuffer.clear()
        }
    }

    internal fun getEvaluator(input: String, useRadians: Boolean = true, radix: Int = 10) =
        ExpressionEvaluator(input, this, mathContext, useRadians, radix)
}