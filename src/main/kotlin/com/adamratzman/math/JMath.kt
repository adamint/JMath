package com.adamratzman.math

import com.adamratzman.math.parser.ExpressionEvaluator
import com.adamratzman.math.parser.ExpressionTokenizer
import java.math.BigDecimal
import java.math.MathContext

data class Expression(
    val input: String,
    val mathContext: MathContext = MathContext.DECIMAL128,
    val tokenizer: ExpressionTokenizer = ExpressionTokenizer(mathContext)
) {
    val evaluator: ExpressionEvaluator by lazy { tokenizer.getEvaluator(input) }
    val variables: MutableMap<String, (ExpressionEvaluator) -> BigDecimal>
            by lazy { evaluator.variables }

    fun evaluate() = evaluator.evaluate()

    fun toString(infix: Boolean = true) =
        if (infix) input.trim()
        else evaluator.inputToString()

    override fun toString() = toString(true)
}

