package com.adamratzman.math.parser

import com.adamratzman.math.Expression

fun main(args: Array<String>) {
    while (true) {
        val input = readLine() ?: continue

        println(Expression(input).evaluate())
    }
}