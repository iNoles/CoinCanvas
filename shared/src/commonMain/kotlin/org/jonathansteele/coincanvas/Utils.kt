package org.jonathansteele.coincanvas

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.toFixed(decimals: Int): String {
    val factor = 10.0.pow(decimals)
    val rounded = (this * factor).roundToInt() / factor
    val text = rounded.toString()

    return if (!text.contains(".")) {
        text + "." + "0".repeat(decimals)
    } else {
        val parts = text.split(".")
        parts[0] + "." + parts[1].padEnd(decimals, '0')
    }
}

fun Double.pretty(): String {
    val plain = this.toString().let {
        if (it.contains('E') || it.contains('e')) this.toLong().toString()
        else it
    }

    val parts = plain.split(".")
    val intPart = parts[0].reversed().chunked(3).joinToString(",").reversed()
    val decPart = parts.getOrNull(1)?.padEnd(2, '0')?.take(2) ?: "00"

    return "$intPart.$decPart"
}