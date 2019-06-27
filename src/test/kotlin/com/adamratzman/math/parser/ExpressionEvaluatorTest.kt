package com.adamratzman.math.parser

import com.adamratzman.math.Expression
import org.junit.Test

internal class ExpressionEvaluatorTest {
    @Test
    fun manual() {
        while (true) {
            val input = readLine() ?: continue

            println(Expression(input).evaluate())
        }
    }
}

fun main(args: Array<String>) {
    while (true) {
        val input = readLine() ?: continue

        println(Expression(input).evaluate())
    }
}