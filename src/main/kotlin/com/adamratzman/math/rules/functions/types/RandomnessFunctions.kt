package com.adamratzman.math.rules.functions.types

import com.adamratzman.math.parser.ExpressionEvaluator
import com.adamratzman.math.rules.functions.FunctionOverloadDescription
import com.adamratzman.math.rules.functions.FunctionType
import com.adamratzman.math.rules.functions.MathFunction
import java.math.BigDecimal
import kotlin.random.Random

class Random : MathFunction(
    "random", FunctionType.RANDOM, 1, 0,
    FunctionOverloadDescription(description = "Generates a decimal between 0 and 1"),
    FunctionOverloadDescription("integer seed", "Generates a decimal between 0 and 1 with the given seed")
) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return if (parameters.isEmpty()) Random.nextDouble().toBigDecimal()
        else Random(parameters[0].toLong()).nextDouble().toBigDecimal()
    }
}

class Randint : MathFunction(
    "randint", FunctionType.RANDOM, 3, 0,
    FunctionOverloadDescription(description = "Generates a random integer from MIN_INT to MAX_INT"),
    FunctionOverloadDescription(
        "integer seed",
        description = "Generate a random integer from MIN_INT to MAX_INT with the given seed"
    ),
    FunctionOverloadDescription(
        "lower bound",
        "upper bound",
        description = "Generate a random integer between the given bounds"
    ),
    FunctionOverloadDescription(
        "integer seed",
        "lower bound",
        "upper bound",
        description = "Generate a random integer between the given bounds with the given seed"
    )
) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return when (parameters.size) {
            0 -> Random.nextInt()
            1 -> Random.nextInt(parameters[0].toInt())
            2 -> Random.nextInt(parameters[0].toInt(), parameters[1].toInt())
            3 -> Random(parameters[0].toInt()).nextInt(parameters[0].toInt(), parameters[1].toInt())
            else -> throw IllegalStateException()
        }.toBigDecimal()
    }
}

class Randlong : MathFunction(
    "randlong", FunctionType.RANDOM, 3, 0,
    FunctionOverloadDescription(description = "Generates a random long from MIN_LONG to MAX_LONG"),
    FunctionOverloadDescription(
        "long seed",
        description = "Generate a random integer from MIN_LONG to MAX_LONG with the given seed"
    ),
    FunctionOverloadDescription(
        "lower bound",
        "upper bound",
        description = "Generate a random long between the given bounds"
    ),
    FunctionOverloadDescription(
        "long seed",
        "lower bound",
        "upper bound",
        description = "Generate a random long between the given bounds with the given seed"
    )
) {
    override fun compute(parameters: List<BigDecimal>, evaluator: ExpressionEvaluator): BigDecimal {
        return when (parameters.size) {
            0 -> Random.nextLong()
            1 -> Random.nextLong(parameters[0].toLong())
            2 -> Random.nextLong(parameters[0].toLong(), parameters[1].toLong())
            3 -> Random(parameters[0].toInt()).nextLong(parameters[0].toLong(), parameters[1].toLong())
            else -> throw IllegalStateException()
        }.toBigDecimal()
    }
}

