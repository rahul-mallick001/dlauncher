package app.olauncher.helper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FuzzySearchEngineTest {

    @Test
    fun testExactAndPrefixMatches() {
        val exactScore = FuzzySearchEngine.matchScore("Camera", "Camera")
        val prefixScore = FuzzySearchEngine.matchScore("Camera", "Cam")
        val noMatchScore = FuzzySearchEngine.matchScore("Camera", "Photos")

        assertTrue(exactScore > prefixScore)
        assertTrue(prefixScore > 0)
        assertEquals(-1, noMatchScore)
    }

    @Test
    fun testWordBoundaryMatches() {
        val psScore = FuzzySearchEngine.matchScore("Play Store", "ps")
        assertTrue("ps should match Play Store", psScore > 0)

        val ytScore = FuzzySearchEngine.matchScore("YouTube Music", "yt")
        assertTrue("yt should match YouTube Music", ytScore > 0)
    }

    @Test
    fun testT9KeypadSearch() {
        // T9 for "play" is 7529
        val t9Play = FuzzySearchEngine.matchScore("Play Store", "7529")
        assertTrue("7529 should match Play", t9Play > 0)

        // T9 for "cam" is 226
        val t9Cam = FuzzySearchEngine.matchScore("Camera", "226")
        assertTrue("226 should match Camera", t9Cam > 0)
    }

    @Test
    fun testFuzzySubsequence() {
        val score = FuzzySearchEngine.matchScore("Instagram", "inst")
        assertTrue("inst should match Instagram", score > 0)
    }
}
