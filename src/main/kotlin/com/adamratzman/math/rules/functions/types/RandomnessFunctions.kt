package com.adamratzman.math.rules.functions.types

import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import java.math.BigDecimal
import java.math.MathContext
import kotlin.random.Random

class Random : MathFunction("random",FunctionType.RANDOM, 1, 0) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return if (parameters.isEmpty()) Random.nextDouble().toBigDecimal()
        else Random(parameters[0].toLong()).nextDouble().toBigDecimal()
    }
}

class Randint : MathFunction("randint",FunctionType.RANDOM, 3, 1) {
    override fun compute(parameters: List<BigDecimal>, context: MathContext): BigDecimal {
        return when (parameters.size) {
            1 -> Random.nextInt(parameters[0].toInt())
            2 -> Random.nextInt(parameters[0].toInt(), parameters[1].toInt())
            3 -> Random(parameters[0].toInt()).nextInt(parameters[0].toInt(), parameters[1].toInt())
            else -> throw IllegalStateException()
        }.toBigDecimal()
    }
}
