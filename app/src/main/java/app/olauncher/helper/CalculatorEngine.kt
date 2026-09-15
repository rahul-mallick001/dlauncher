package app.olauncher.helper

import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt

object CalculatorEngine {

    private val decimalFormat = DecimalFormat("#,##0.########")

    /**
     * Attempts to evaluate [query] as a math expression.
     * Returns the formatted result string if valid, or null if not a valid math expression.
     */
    fun evaluate(query: String?): String? {
        if (query.isNullOrBlank()) return null
        val trimmed = query.trim()

        // Do not trigger calculator on pure alphabetic app searches
        if (!trimmed.any { it.isDigit() }) return null

        // Support "X% of Y" syntax
        val percentOfMatch = Regex("""^(\d+(?:\.\d+)?)\s*%\s*(?:of)?\s*(\d+(?:\.\d+)?)$""", RegexOption.IGNORE_CASE).matchEntire(trimmed)
        if (percentOfMatch != null) {
            val (pctStr, valStr) = percentOfMatch.destructured
            val pct = pctStr.toDoubleOrNull() ?: return null
            val value = valStr.toDoubleOrNull() ?: return null
            val result = (pct / 100.0) * value
            return formatResult(result)
        }

        // Clean query: replace special math symbols
        var sanitized = trimmed
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
            .replace("x", "*")
            .replace(",", "")
            .trim()

        // Ensure expression contains at least one arithmetic operator or math function
        val containsOperator = sanitized.any { it in "+-*/%^" } || sanitized.startsWith("sqrt", ignoreCase = true)
        if (!containsOperator) return null

        // Handle percentage operations like "500 - 10%" or "200 + 15%"
        sanitized = sanitizePercentage(sanitized)

        return try {
            val result = parseExpression(sanitized)
            if (result.isNaN() || result.isInfinite()) null
            else formatResult(result)
        } catch (_: Exception) {
            null
        }
    }

    private fun sanitizePercentage(expr: String): String {
        val pattern = Regex("""(\d+(?:\.\d+)?)\s*([+\-])\s*(\d+(?:\.\d+)?)\s*%""")
        return pattern.replace(expr) { match ->
            val base = match.groupValues[1]
            val op = match.groupValues[2]
            val pct = match.groupValues[3].toDoubleOrNull() ?: 0.0
            "$base $op ($base * ${pct / 100.0})"
        }
    }

    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0 && value in -1e14..1e14) {
            String.format("= %,d", value.toLong())
        } else {
            "= " + decimalFormat.format(value)
        }
    }

    private fun parseExpression(expression: String): Double {
        val tokens = tokenize(expression)
        val parser = ExpressionParser(tokens)
        return parser.parse()
    }

    private fun tokenize(expr: String): List<String> {
        val tokens = mutableListOf<String>()
        var i = 0
        while (i < expr.length) {
            val c = expr[i]
            when {
                c.isWhitespace() -> i++
                c in "+-*/^()" -> {
                    tokens.add(c.toString())
                    i++
                }
                c == '%' -> {
                    tokens.add("%")
                    i++
                }
                c.isDigit() || c == '.' -> {
                    val start = i
                    while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                        i++
                    }
                    tokens.add(expr.substring(start, i))
                }
                expr.startsWith("sqrt", i, ignoreCase = true) -> {
                    tokens.add("sqrt")
                    i += 4
                }
                else -> throw IllegalArgumentException("Invalid character: $c")
            }
        }
        return tokens
    }

    private class ExpressionParser(private val tokens: List<String>) {
        private var pos = 0

        fun parse(): Double {
            val result = parseAddSub()
            if (pos < tokens.size) {
                throw IllegalArgumentException("Unexpected token: ${tokens[pos]}")
            }
            return result
        }

        private fun parseAddSub(): Double {
            var value = parseMulDiv()
            while (pos < tokens.size && (tokens[pos] == "+" || tokens[pos] == "-")) {
                val op = tokens[pos++]
                val nextVal = parseMulDiv()
                value = if (op == "+") value + nextVal else value - nextVal
            }
            return value
        }

        private fun parseMulDiv(): Double {
            var value = parseExponent()
            while (pos < tokens.size && (tokens[pos] == "*" || tokens[pos] == "/" || tokens[pos] == "%")) {
                val op = tokens[pos++]
                val nextVal = parseExponent()
                value = when (op) {
                    "*" -> value * nextVal
                    "/" -> {
                        if (nextVal == 0.0) throw ArithmeticException("Division by zero")
                        value / nextVal
                    }
                    "%" -> value % nextVal
                    else -> value
                }
            }
            return value
        }

        private fun parseExponent(): Double {
            var value = parseFactor()
            while (pos < tokens.size && tokens[pos] == "^") {
                tokens[pos++]
                val nextVal = parseFactor()
                value = value.pow(nextVal)
            }
            return value
        }

        private fun parseFactor(): Double {
            if (pos >= tokens.size) throw IllegalArgumentException("Unexpected end of expression")
            val token = tokens[pos++]

            return when {
                token == "+" -> parseFactor()
                token == "-" -> -parseFactor()
                token == "sqrt" -> {
                    val inner = parseFactor()
                    if (inner < 0) throw IllegalArgumentException("Square root of negative number")
                    sqrt(inner)
                }
                token == "(" -> {
                    val value = parseAddSub()
                    if (pos >= tokens.size || tokens[pos++] != ")") {
                        throw IllegalArgumentException("Missing closing parenthesis")
                    }
                    value
                }
                token.toDoubleOrNull() != null -> token.toDouble()
                else -> throw IllegalArgumentException("Invalid token: $token")
            }
        }
    }
}
