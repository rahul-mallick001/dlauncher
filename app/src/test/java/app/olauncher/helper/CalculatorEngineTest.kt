package app.olauncher.helper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class CalculatorEngineTest {

    @Test
    fun testBasicCalculations() {
        assertEquals("= 4", CalculatorEngine.evaluate("2 + 2"))
        assertEquals("= 14", CalculatorEngine.evaluate("2 + 3 * 4"))
        assertEquals("= 20", CalculatorEngine.evaluate("(2 + 3) * 4"))
        assertEquals("= 25", CalculatorEngine.evaluate("100 / 4"))
        assertEquals("= 256", CalculatorEngine.evaluate("2 ^ 8"))
    }

    @Test
    fun testPercentages() {
        assertEquals("= 20", CalculatorEngine.evaluate("10% of 200"))
        assertEquals("= 450", CalculatorEngine.evaluate("500 - 10%"))
        assertEquals("= 115", CalculatorEngine.evaluate("100 + 15%"))
    }

    @Test
    fun testSqrt() {
        assertEquals("= 8", CalculatorEngine.evaluate("sqrt(64)"))
        assertEquals("= 12", CalculatorEngine.evaluate("sqrt(144)"))
    }

    @Test
    fun testInvalidOrNonMathInputs() {
        assertNull(CalculatorEngine.evaluate("camera"))
        assertNull(CalculatorEngine.evaluate("whatsapp"))
        assertNull(CalculatorEngine.evaluate("2"))
        assertNull(CalculatorEngine.evaluate(""))
        assertNull(CalculatorEngine.evaluate("   "))
    }
}
