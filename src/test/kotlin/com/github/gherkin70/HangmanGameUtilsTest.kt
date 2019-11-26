package com.github.gherkin70

import com.github.gherkin70.hangman.HangmanGameUtils
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class HangmanGameUtilsTest {
    private val testClass = HangmanGameUtils(WORD, GUESSES)

    @Test
    fun `should win when all letters marked as answered`() {
        guessLetter('T')
        assertWon(false)
        guessLetter('E')
        assertWon(false)
        guessLetter('S')
        assertWon(true)
    }

    @Test
    fun `should win when whole word is guessed`() {
        guessWord("ABC")
        assertWon(false)
        guessWord("TEST")
        assertWon(true)
    }

    @Test
    fun `should lose when guesses depleted to zero`() {
        guessLetter('A')
        assertLost(false)
        guessLetter('B')
        assertLost(true)
    }

    @Test
    fun `guessed letters should be considered duplicate`() {
        // correct answer
        guessLetter('T')
        checkDuplicate('T')

        // incorrect answer
        guessLetter('A')
        checkDuplicate('A')
    }

    private fun guessLetter(letter: Char) = testClass.answerLetter(letter)

    private fun guessWord(word: String) = testClass.answerWord(word)

    private fun checkDuplicate(letter: Char) = assertThat(testClass.checkDuplicateAnswer(letter), equalTo(true))

    private fun assertWon(won: Boolean) = assertThat(testClass.checkGameWon(), equalTo(won))

    private fun assertLost(lost: Boolean) = assertThat(testClass.checkGameLost(), equalTo(lost))

    companion object {
        private const val WORD = "TEST"
        private const val GUESSES = 2
    }
}
