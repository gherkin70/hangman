package com.github.gherkin70.hangman

import org.slf4j.LoggerFactory

class HangmanGameUtils(
    wordToGuess: String,
    guesses: Int
) {
    private var state: HangmanState = HangmanState(
        wordToGuess = wordToGuess.toUpperCase(),
        remainingGuesses = guesses
    )

    fun answerLetter(letter: Char) {
        logger.info("Answering with the letter $letter")
        val correct = state.correctLetter(letter)
        state = if (correct) {
            logger.info("'$letter' is a correct guess.")
            // letter is a correct guess, mark all matching letters as answered
            val answeredLetters = state.markLetterAsAnswered(letter)
            state.copy(words = answeredLetters)
        } else {
            logger.info("'$letter' is an incorrect guess.")
            // incorrect guess, add letter to incorrect guesses and deduct from remaining guesses
            state.copy(
                incorrectGuesses = state.incorrectGuesses + letter,
                remainingGuesses = state.remainingGuesses - 1
            )
        }
    }

    fun answerWord(word: String) {
        val trimWord = word.trim()
        logger.info("Answering with the word $word")
        state = if (state.correctWord(trimWord)) {
            logger.info("'$trimWord' is a correct guess.")
            // word is correct, mark the word as fully answered
            state.copy(words = state.markAllLettersAsAnswered())
        } else {
            logger.info("'$trimWord' is an incorrect guess.")
            // incorrect guess, deduct from remaining guesses
            state.copy(
                remainingGuesses = state.remainingGuesses - 1
            )
        }
    }

    fun printGameState() {
        println(remainingGuessesString())
        println(guessedWordsString())
        println(incorrectWordsString())
    }

    fun logGameState() {
        logger.debug(remainingGuessesString())
        logger.debug(guessedWordsString())
        logger.debug(incorrectWordsString())
    }

    fun checkGameWon() = state.gameWon()

    fun checkGameLost() = state.gameLost()

    fun checkDuplicateAnswer(letter: Char) = state.isDuplicateLetter(letter)

    fun getWord() = state.wordToGuess

    private fun remainingGuessesString() = "${state.remainingGuesses} guesses left"

    private fun guessedWordsString() = "${state.words.map { word -> word.map { if (it.answered) it.letter else '_' } }}"

    private fun incorrectWordsString() = "Incorrect words: ${state.incorrectGuesses}"

    companion object {
        private val logger = LoggerFactory.getLogger(HangmanGameUtils::class.java)
    }
}
