package com.mathews.orgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.brlFormatter(): String {
    val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"));

    return formatter.format(this);
}