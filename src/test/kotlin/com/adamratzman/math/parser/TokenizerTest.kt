package com.adamratzman.math.parser

import com.adamratzman.math.utils.round
import com.adamratzman.math.utils.trim
import org.junit.Test
import java.math.MathContext

internal class TokenizerTest {
    val tokenizer = ExpressionTokenizer(MathContext.DECIMAL128)

    @Test
    fun tokenize() {
        println(tokenizer.tokenize("11 + 4sin(304.9)"))
        println(tokenizer.tokenize("31 + 4y * 22 / (1 - sin(5)) ^ 2 ^ 36"))
        println(tokenizer.tokenize("3+4*2/(1-5)^2^3"))
        println(tokenizer.tokenize("sin ( max (2, 3) / 3 * pi )"))

        println(tokenizer.tokenize("-4 + 9"))
    }

    @Test
    fun evaluate() {
        val evaluator = tokenizer.getEvaluator("31 + 4*36 * 22 / (1 - sin(5)) ^ 2 ^ 36")
        evaluator.variables["y"] = { _ -> 36.toBigDecimal() }

        println("Infix: ${evaluator.programInput}")
        println("Postfix: ${evaluator.inputToString()}")

        println(evaluator.evaluate().round(6).trim())
    }

    @Test
    fun unary() {
        val evaluator = tokenizer.getEvaluator("-31(-4)+---9")

        println("Infix: ${evaluator.programInput}")
        println("Postfix: ${evaluator.inputToString()}")

        println(evaluator.evaluate().round(6).trim())
    }
}