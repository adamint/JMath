package com.adamratzman.math.rules.functions.types

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.parser.ExpressionEvaluator
import com.adamratzman.math.rules.functions.FunctionOverloadDescription
import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import com.google.common.math.BigIntegerMath
import java.math.BigDecimal

class Factorial : MathFunction("factorial", FunctionType.ALGEBRAIC, 1) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.factorial(parameters[0], evaluator.context)
    }
}

class Log : MathFunction("log", FunctionType.ALGEBRAIC, 2, 1) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return if (parameters.size == 1) BigDecimalMath.log10(parameters[0], evaluator.context)
        else BigDecimalMath.log10(parameters[0], evaluator.context)
            .divide(BigDecimalMath.log10(parameters[1], evaluator.context), evaluator.context)
    }
}

class Remainder : MathFunction("remainder", FunctionType.ALGEBRAIC, 2, aliases = listOf("rem")) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return parameters[0].remainder(parameters[1], evaluator.context)
    }
}

class Root : MathFunction(
    "nroot", FunctionType.ALGEBRAIC, 2, 2,
    FunctionOverloadDescription("number, root", description = "Computes first^(1/root)")
) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.root(parameters[0], parameters[1], evaluator.context)
    }
}

class Sqrt : MathFunction("squareroot", FunctionType.ALGEBRAIC, 1, aliases = listOf("sqrt")) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.sqrt(parameters[0], evaluator.context)
    }
}

class Abs : MathFunction("absolute", FunctionType.ALGEBRAIC, 1, aliases = listOf("abs")) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return parameters[0].abs(evaluator.context)
    }
}

class Pow : MathFunction(
    "pow", FunctionType.ALGEBRAIC, 2, 2,
    FunctionOverloadDescription("first, second", description = "Computes first^second"),
    aliases = listOf("power")
) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.pow(parameters[0], parameters[1], evaluator.context)
    }
}

class Exp : MathFunction("exp", FunctionType.ALGEBRAIC, 1, aliases = listOf("exponential")) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.exp(parameters[0], evaluator.context)
    }
}

class Gamma : MathFunction("gamma", FunctionType.ALGEBRAIC, 1) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.gamma(parameters[0], evaluator.context)
    }
}

class Reciprocal : MathFunction("reciprocal", FunctionType.ALGEBRAIC, 1) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.reciprocal(parameters[0], evaluator.context)
    }
}

class Binomial : MathFunction(
    "binomial", FunctionType.ALGEBRAIC, 2, 2,
    FunctionOverloadDescription("n, k", description = "Computes n! / (k! (n - k)!)")
) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigIntegerMath.binomial(parameters[0].toInt(), parameters[1].toInt()).toBigDecimal()
    }
}