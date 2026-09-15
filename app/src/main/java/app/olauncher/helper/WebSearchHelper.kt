package app.olauncher.helper

import android.content.Context
import java.net.URLEncoder

object WebSearchHelper {

    enum class SearchEngine(val prefix: String, val displayName: String, val urlTemplate: String) {
        DUCK_DUCK_GO("d", "DuckDuckGo", "https://duckduckgo.com/?q=%s"),
        GOOGLE("g", "Google", "https://www.google.com/search?q=%s"),
        YOUTUBE("y", "YouTube", "https://www.youtube.com/results?search_query=%s"),
        WIKIPEDIA("w", "Wikipedia", "https://en.wikipedia.org/wiki/Special:Search?search=%s"),
        REDDIT("r", "Reddit", "https://www.reddit.com/search/?q=%s"),
        MAPS("m", "Google Maps", "https://www.google.com/maps/search/%s"),
        BING("b", "Bing", "https://www.bing.com/search?q=%s")
    }

    /**
     * Checks if the query starts with a recognized engine prefix (e.g. "g query", "!query", "y: query").
     * Returns a pair of (SearchEngine, queryText) if matched, or null otherwise.
     */
    fun parseCustomSearch(query: String?): Pair<SearchEngine, String>? {
        if (query.isNullOrBlank()) return null
        val trimmed = query.trim()

        // DuckDuckGo Bangs format (!bang or !query)
        if (trimmed.startsWith("!")) {
            val q = trimmed.removePrefix("!").trim()
            return Pair(SearchEngine.DUCK_DUCK_GO, "!" + q)
        }

        // Prefix formats: "g query", "g: query", "y query", etc.
        val spaceIndex = trimmed.indexOfFirst { it == ' ' || it == ':' }
        if (spaceIndex in 1..2) {
            val prefix = trimmed.substring(0, spaceIndex).lowercase()
            val actualQuery = trimmed.substring(spaceIndex + 1).trim()
            if (actualQuery.isNotEmpty()) {
                val engine = SearchEngine.values().find { it.prefix == prefix }
                if (engine != null) {
                    return Pair(engine, actualQuery)
                }
            }
        }

        return null
    }

    /**
     * Executes the web search for the given engine and search query.
     */
    fun executeSearch(context: Context, engine: SearchEngine, query: String) {
        val encoded = try {
            URLEncoder.encode(query, "UTF-8")
        } catch (_: Exception) {
            query.replace(" ", "%20")
        }
        val url = String.format(engine.urlTemplate, encoded)
        context.openUrl(url)
    }
}
