package com.adamratzman.math.rules.functions.types

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.parser.ExpressionEvaluator
import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import java.math.BigDecimal
import java.math.RoundingMode

class ToDegree : MathFunction("deg", FunctionType.CONVERSION, 1, aliases = listOf("degrees", "d")) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return if (!evaluator.useRadians) parameters[0]
        else parameters[0].times(BigDecimal.valueOf(180)).divide(
            BigDecimalMath.pi(evaluator.context),
            evaluator.context
        )
    }
}

class ToRadians : MathFunction("rad", FunctionType.CONVERSION, 1, aliases = listOf("radians", "r")) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return if (evaluator.useRadians) parameters[0]
        else parameters[0].times(BigDecimalMath.pi(evaluator.context)).divide(
            BigDecimal.valueOf(180),
            evaluator.context
        )
    }
}

class Ceil : MathFunction("ceil", FunctionType.CONVERSION, 1) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return parameters[0].setScale(0, RoundingMode.CEILING)
    }
}

class Floor : MathFunction("floor", FunctionType.CONVERSION, 1) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return parameters[0].setScale(0, RoundingMode.FLOOR)
    }
}

class Round : MathFunction("round", FunctionType.CONVERSION, 1) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return parameters[0].setScale(0, RoundingMode.HALF_UP)
    }
}

class RoundDown : MathFunction("rounddown", FunctionType.CONVERSION, 1) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return parameters[0].setScale(0, RoundingMode.HALF_DOWN)
    }
}