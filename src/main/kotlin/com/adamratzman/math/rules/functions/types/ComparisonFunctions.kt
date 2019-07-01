package com.adamratzman.math.rules.functions.types

import com.adamratzman.math.parser.ExpressionEvaluator
import com.adamratzman.math.rules.functions.FunctionOverloadDescription
import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import java.math.BigDecimal

class Max : MathFunction("max", FunctionType.COMPARISON,2,
    parameters = *arrayOf(FunctionOverloadDescription("variadic number list"))) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        val first = parameters[0]
        val second = parameters[1]

        return if (first > second) first else second
    }
}