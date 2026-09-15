package app.olauncher.helper

object FuzzySearchEngine {

    private val t9Map = mapOf(
        '2' to charArrayOf('a', 'b', 'c'),
        '3' to charArrayOf('d', 'e', 'f'),
        '4' to charArrayOf('g', 'h', 'i'),
        '5' to charArrayOf('j', 'k', 'l'),
        '6' to charArrayOf('m', 'n', 'o'),
        '7' to charArrayOf('p', 'q', 'r', 's'),
        '8' to charArrayOf('t', 'u', 'v'),
        '9' to charArrayOf('w', 'x', 'y', 'z')
    )

    /**
     * Calculates a matching score between [target] text and search [query].
     * Returns a score >= 0 if matched, or -1 if no match.
     * Higher score represents a better, more relevant match.
     */
    fun matchScore(target: String, query: String): Int {
        if (query.isEmpty()) return 100
        val targetLower = target.lowercase().trim()
        val queryLower = query.lowercase().trim()

        // 1. Exact match
        if (targetLower == queryLower) return 1000

        // 2. Starts with query
        if (targetLower.startsWith(queryLower)) return 800 - (targetLower.length - queryLower.length)

        // 3. Word boundary match (e.g. "p s" or "ps" matching "Play Store", "w" matching "WhatsApp")
        val words = targetLower.split(" ", "_", "-", ".", "/")
        val wordStarts = words.mapNotNull { it.firstOrNull() }.joinToString("")
        if (wordStarts.startsWith(queryLower)) return 700
        if (wordStarts.contains(queryLower)) return 650

        // Check if any word starts with query
        for ((index, word) in words.withIndex()) {
            if (word.startsWith(queryLower)) {
                return 600 - (index * 10)
            }
        }

        // 4. Substring match
        val subIndex = targetLower.indexOf(queryLower)
        if (subIndex >= 0) {
            return 500 - subIndex
        }

        // 5. T9 numeric keypad match (if query consists entirely of digits 2-9)
        if (queryLower.all { it in '2'..'9' }) {
            val t9Score = matchT9(targetLower, queryLower)
            if (t9Score > 0) return t9Score
        }

        // 6. Fuzzy subsequence matching (characters appear in order)
        var tIdx = 0
        var qIdx = 0
        var matches = 0
        var consecutive = 0
        var bonus = 0

        while (tIdx < targetLower.length && qIdx < queryLower.length) {
            if (targetLower[tIdx] == queryLower[qIdx]) {
                matches++
                qIdx++
                consecutive++
                bonus += consecutive * 5
            } else {
                consecutive = 0
            }
            tIdx++
        }

        if (qIdx == queryLower.length) {
            val ratio = (matches.toFloat() / targetLower.length) * 100
            return (ratio.toInt() + bonus).coerceAtLeast(1)
        }

        return -1
    }

    private fun matchT9(target: String, digits: String): Int {
        val targetClean = target.filter { it.isLetter() }.lowercase()
        if (targetClean.length < digits.length) return -1

        for (start in 0..(targetClean.length - digits.length)) {
            var matched = true
            for (i in digits.indices) {
                val digit = digits[i]
                val validChars = t9Map[digit] ?: charArrayOf()
                if (targetClean[start + i] !in validChars) {
                    matched = false
                    break
                }
            }
            if (matched) {
                return if (start == 0) 550 else 450 - start
            }
        }
        return -1
    }
}
