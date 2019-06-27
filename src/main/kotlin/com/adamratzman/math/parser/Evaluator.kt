package com.adamratzman.math.parser

import com.adamratzman.math.rules.NumberToken
import com.adamratzman.math.rules.Token
import com.adamratzman.math.rules.VariableToken
import com.adamratzman.math.rules.functions.UnaryOperator
import java.math.BigDecimal
import java.math.MathContext

abstract class AbstractEvaluator(val programInput: String, val tokenizer: ExpressionTokenizer, val context: MathContext) {
    val tokenizedProgram by lazy { tokenizer.tokenize(programInput).toMutableList() }

    val variables = mutableMapOf<String, () -> BigDecimal>()

    internal fun replaceVariables(): MutableList<Token> {
        val tokens = tokenizedProgram.toMutableList()

        variables.forEach { variable ->
            tokens.mapIndexedNotNull { i, token ->
                if (token is VariableToken && token.token == variable.key) i
                else null
            }.forEach { indexToChange ->
                tokens[indexToChange] = NumberToken(variable.value().toPlainString())
            }
        }

        return tokens
    }

    abstract fun evaluate(): BigDecimal

    fun toString(strictRpnNotation: Boolean = true): String {
        return if (!strictRpnNotation) replaceVariables().toString()
        else replaceVariables().joinToString(" ") { if (it is UnaryOperator) "u${it.token}" else it.token }
    }
}