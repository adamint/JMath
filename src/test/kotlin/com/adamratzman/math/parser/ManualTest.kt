package com.adamratzman.math.parser

import com.adamratzman.math.Expression
import com.adamratzman.math.utils.round

fun main() {
    while (true) {
        val input = readLine() ?: continue

        println(Expression(input).evaluate().round(6).stripTrailingZeros())
    }
}