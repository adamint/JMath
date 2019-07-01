package com.adamratzman.math

import com.adamratzman.math.parser.ExpressionEvaluator
import com.adamratzman.math.parser.ExpressionTokenizer
import java.math.BigDecimal
import java.math.MathContext

data class Expression(
    val input: String,
    val useRadians: Boolean = true,
    val radix: Int = 10,
    val mathContext: MathContext = MathContext.DECIMAL128,
    val tokenizer: ExpressionTokenizer = ExpressionTokenizer(mathContext)
) {
    val evaluator: ExpressionEvaluator by lazy { tokenizer.getEvaluator(input, useRadians, radix) }
    val variables: MutableMap<String, () -> BigDecimal>
            by lazy { evaluator.variables }

    fun evaluate(): BigDecimal {
        evaluator.variables = variables

        return evaluator.evaluate()
    }

    fun toString(infix: Boolean = true) =
        if (infix) input.trim()
        else evaluator.toString(!infix)

    override fun toString() = toString(true)
}

