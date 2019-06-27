package com.adamratzman.math.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.round(place: Int) = setScale(place, RoundingMode.HALF_UP)

fun BigDecimal.trim() = stripTrailingZeros()