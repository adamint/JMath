package com.adamratzman.math.rules.functions.types

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.parser.ExpressionEvaluator
import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import com.adamratzman.math.rules.functions.TrigFunction
import java.math.BigDecimal

class Sine : TrigFunction("sine",1, aliases = listOf("sin")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.sin(parameters[0], evaluator.context)
    }
}

class Cosine : TrigFunction("cosine", 1, aliases = listOf("cos")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.cos(parameters[0], evaluator.context)
    }
}

class Tangent : TrigFunction("tangent", 1, aliases = listOf("tan")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.tan(parameters[0], evaluator.context)
    }
}

class Cosecant : TrigFunction("cosecant", 1, aliases = listOf("csc")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimal.ONE.divide(BigDecimalMath.sin(parameters[0], evaluator.context), evaluator.context)
    }
}

class Cotangent : TrigFunction("cotangent", 1, aliases = listOf("cot")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.cot(parameters[0], evaluator.context)
    }
}

class Secant : TrigFunction("secant", 1, aliases = listOf("sec")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimal.ONE.divide(BigDecimalMath.cos(parameters[0], evaluator.context), evaluator.context)
    }
}

class Arcsin : TrigFunction("arcsin", 1, aliases = listOf("asin")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.asin(parameters[0], evaluator.context)
    }
}

class Arccos : TrigFunction("arccos", 1, aliases = listOf("acos")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.acos(parameters[0], evaluator.context)
    }
}

class Arctan : TrigFunction("arctan", 1, aliases = listOf("atan")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.atan(parameters[0], evaluator.context)
    }
}

class Arctan2 : TrigFunction("arctan2", 2, aliases = listOf("atan2")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.atan2(parameters[0], parameters[1], evaluator.context)
    }
}

class Arccotangent : TrigFunction("arccot", 1, aliases = listOf("acot")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.acot(parameters[0], evaluator.context)
    }
}

class Arcsecant : TrigFunction("arcsec", 1, aliases = listOf("asec")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.acos(BigDecimal.ONE.divide(parameters[0], evaluator.context), evaluator.context)
    }
}


class Arccosecant : TrigFunction("arccsc", 1, aliases = listOf("acsc")) {
    override fun computeRadians(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return BigDecimalMath.asin(BigDecimal.ONE.divide(parameters[0], evaluator.context), evaluator.context)
    }
}