package app.olauncher.helper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class WebSearchHelperTest {

    @Test
    fun testDuckDuckGoBangs() {
        val parsed = WebSearchHelper.parseCustomSearch("!w kotlin")
        assertNotNull(parsed)
        assertEquals(WebSearchHelper.SearchEngine.DUCK_DUCK_GO, parsed?.first)
        assertEquals("!w kotlin", parsed?.second)
    }

    @Test
    fun testPrefixEngines() {
        val google = WebSearchHelper.parseCustomSearch("g android studio")
        assertNotNull(google)
        assertEquals(WebSearchHelper.SearchEngine.GOOGLE, google?.first)
        assertEquals("android studio", google?.second)

        val youtube = WebSearchHelper.parseCustomSearch("y: lofi music")
        assertNotNull(youtube)
        assertEquals(WebSearchHelper.SearchEngine.YOUTUBE, youtube?.first)
        assertEquals("lofi music", youtube?.second)

        val wiki = WebSearchHelper.parseCustomSearch("w open source")
        assertNotNull(wiki)
        assertEquals(WebSearchHelper.SearchEngine.WIKIPEDIA, wiki?.first)
        assertEquals("open source", wiki?.second)
    }

    @Test
    fun testRegularQuery() {
        val regular = WebSearchHelper.parseCustomSearch("calculator")
        assertNull(regular)
    }
}
