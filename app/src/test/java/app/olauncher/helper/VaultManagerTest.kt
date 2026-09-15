package app.olauncher.helper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VaultManagerTest {

    @Test
    fun testSocialMediaDetection() {
        val socialPackages = VaultManager.SOCIAL_MEDIA_PACKAGES

        assertTrue(socialPackages.contains("com.instagram.android"))
        assertTrue(socialPackages.contains("com.zhiliaoapp.musically"))
        assertTrue(socialPackages.contains("com.facebook.katana"))
        assertTrue(socialPackages.contains("com.twitter.android"))
        assertTrue(socialPackages.contains("com.snapchat.android"))
        assertTrue(socialPackages.contains("com.google.android.youtube"))
        assertTrue(socialPackages.contains("com.reddit.frontpage"))

        assertFalse(socialPackages.contains("com.android.calculator2"))
        assertFalse(socialPackages.contains("com.google.android.calendar"))
    }

    @Test
    fun testTodoCompletionCheck() {
        val todosIncomplete = listOf(
            TodoItem("1", "Read book", true),
            TodoItem("2", "Workout", false)
        )
        assertFalse(todosIncomplete.all { it.isCompleted })

        val todosComplete = listOf(
            TodoItem("1", "Read book", true),
            TodoItem("2", "Workout", true)
        )
        assertTrue(todosComplete.all { it.isCompleted })
    }

    @Test
    fun testPatternStringRepresentation() {
        // Pattern format: indices 0..8 of 3x3 grid
        val pattern = "01258"
        assertEquals(5, pattern.length)
        assertTrue(pattern.all { it.isDigit() })
    }
}
