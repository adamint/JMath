package com.adamratzman.math.rules.functions.types

import ch.obermuhlner.math.big.BigDecimalMath
import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import java.math.BigDecimal
import java.math.MathContext

class Factorial : MathFunction("factorial", FunctionType.ALGEBRAIC, 1) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return BigDecimalMath.factorial(parameters[0], context)
    }
}

class Log : MathFunction("log", FunctionType.ALGEBRAIC, 2, 1) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return if (parameters.size == 1) BigDecimalMath.log10(parameters[0], context)
        else BigDecimalMath.log10(parameters[0], context)
            .divide(BigDecimalMath.log10(parameters[1], context), context)
    }
}

class Remainder : MathFunction("remainder", FunctionType.ALGEBRAIC, 2, aliases = listOf("rem")) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return parameters[0].remainder(parameters[1], context)
    }
}

class Root : MathFunction("nroot", FunctionType.ALGEBRAIC, 2) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return BigDecimalMath.root(parameters[0], parameters[1], context)
    }
}

class Sqrt : MathFunction("squareroot", FunctionType.ALGEBRAIC, 1, aliases = listOf("sqrt")) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return BigDecimalMath.sqrt(parameters[0],context)
    }
}
