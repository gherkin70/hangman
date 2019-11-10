package com.github.gherkin70

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class HangmanGameUtilsTest {

    private val testClass = HangmanGameUtils(WORD, GUESSES)

    @Test
    fun `should win when all letters marked as answered`() {
        makeAnswer('T')
        assertWon(false)
        makeAnswer('E')
        assertWon(false)
        makeAnswer('S')
        assertWon(true)
    }

    @Test
    fun `should lose when guesses depleted to zero`() {
        makeAnswer('A')
        assertLost(false)
        makeAnswer('B')
        assertLost(true)
    }

    @Test
    fun `guessed letters should be considered duplicate`() {
        // correct answer
        makeAnswer('T')
        checkDuplicate('T')

        // incorrect answer
        makeAnswer('A')
        checkDuplicate('A')
    }

    private fun makeAnswer(letter: Char) = testClass.answer(letter)

    private fun checkDuplicate(letter: Char) = assertThat(testClass.checkDuplicateAnswer(letter), equalTo(true))

    private fun assertWon(won: Boolean) = assertThat(testClass.checkGameWon(), equalTo(won))

    private fun assertLost(lost: Boolean) = assertThat(testClass.checkGameLost(), equalTo(lost))

    companion object {
        private const val WORD = "TEST"
        private const val GUESSES = 2
    }
}
