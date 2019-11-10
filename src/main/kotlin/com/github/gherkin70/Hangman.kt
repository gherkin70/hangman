package com.github.gherkin70

class Hangman(
    wordToGuess: String,
    private val gameUtils: HangmanGameUtils = HangmanGameUtils(wordToGuess, GUESSES)
) {
    fun startGame() {
        var gameFinished = false
        while (!gameFinished) {
            gameUtils.printGameState()
            val letter = promptLetter()
            letter?.let { submitAnswer(it) } ?: println("Please input a single letter.")
            gameFinished = checkGameFinished()
        }
    }

    private fun submitAnswer(letter: Char) {
        val isDuplicateAnswer = gameUtils.checkDuplicateAnswer(letter)
        if (!isDuplicateAnswer) {
            gameUtils.answer(letter)
        } else {
            println("'$letter' has already been used. Pick a different letter.")
        }
    }

    private fun promptLetter(): Char? {
        println("Enter a letter to guess with:")
        return readLine()?.singleOrNull()?.takeIf { it.isLetter() }?.toUpperCase()
    }

    private fun checkGameFinished(): Boolean {
        val won = gameUtils.checkGameWon()
        val lost = gameUtils.checkGameLost()
        return when {
            won -> {
                println("Win! Word complete.")
                true
            }
            lost -> {
                println("Lost! Guessed wrong too many times.")
                true
            }
            else -> false
        }
    }

    companion object {
        private const val GUESSES = 6
    }
}
