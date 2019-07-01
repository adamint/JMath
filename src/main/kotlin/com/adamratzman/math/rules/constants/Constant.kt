package com.adamratzman.math.rules.constants

import com.adamratzman.math.parser.ExpressionEvaluator
import com.adamratzman.math.parser.ExpressionTokenizer
import java.math.BigDecimal
import java.math.MathContext

abstract class Constant(val name: String, val caseSensitive: Boolean = false) {
    abstract fun generate(tokenizer: ExpressionTokenizer): BigDecimal
}

class TrivialConstant(name: String, val value: (ExpressionTokenizer) -> BigDecimal, caseSensitive: Boolean = false)
    : Constant(name, caseSensitive) {
    override fun generate(tokenizer: ExpressionTokenizer) = value(tokenizer)
}

class TypeConstant(name: String, val value: BigDecimal, caseSensitive: Boolean = false) : Constant(name, caseSensitive) {
    override fun generate(tokenizer: ExpressionTokenizer) = value
}