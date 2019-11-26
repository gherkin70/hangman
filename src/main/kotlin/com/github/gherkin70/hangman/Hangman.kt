package com.github.gherkin70.hangman

import com.github.gherkin70.hangman.model.GameType
import com.github.gherkin70.randomwordgenerator.client.RandomWordGeneratorClient
import org.slf4j.LoggerFactory

class Hangman(
    private val wordClient: RandomWordGeneratorClient = RandomWordGeneratorClient()
) {
    private lateinit var gameUtils: HangmanGameUtils

    fun initGame() {
        val gameType = promptGameType()
        val wordLength = if (gameType == GameType.WORD) {
            promptWordLength()
        } else null
        val wordToGuess = getWordToGuess(gameType, wordLength)
        gameUtils = HangmanGameUtils(wordToGuess, GUESSES)
        logger.info("Starting a new game with the answer '$wordToGuess'")
        startGame()
    }

    private fun startGame() {
        var gameFinished = false
        while (!gameFinished) {
            gameUtils.logGameState()
            val guess = promptGuess()
            guess?.let { submitGuess(it) } ?: println("Invalid guess. Only letters are valid")
            gameFinished = checkGameFinished()
        }
    }

    private fun promptWordLength(): Int {
        var wordLength: Int? = null
        while (wordLength == null) {
            println("Enter the number of characters you'd like the word to be:")
            wordLength = readLine()?.toIntOrNull()
        }
        return wordLength
    }

    private fun submitGuess(guess: String) {
        if (guess.length == 1) {
            submitLetter(guess.single())
        } else {
            submitWord(guess)
        }
    }

    private fun submitLetter(letter: Char) {
        val isDuplicateAnswer = gameUtils.checkDuplicateAnswer(letter)
        if (!isDuplicateAnswer) {
            gameUtils.answerLetter(letter)
        } else {
            println("'$letter' has already been used. Pick a different letter.")
        }
    }

    private fun submitWord(word: String) = gameUtils.answerWord(word)

    private fun promptGameType(): GameType {
        var gameType: GameType? = null
        while (gameType == null) {
            println("Play Hangman with a word (W) or a phrase (P)?")
            val input = readLine()?.toUpperCase()
            gameType = when (input) {
                "W" -> GameType.WORD
                "P" -> GameType.PHRASE
                else -> {
                    print("Invalid choice. Choices are 'W' or 'P'")
                    null
                }
            }
        }
        return gameType
    }

    private fun getWordToGuess(gameType: GameType, wordLength: Int?) = when (gameType) {
        GameType.WORD -> wordClient.getRandomWord(wordLength!!)
        GameType.PHRASE -> wordClient.getRandomPhrase()
    }

    private fun promptGuess(): String? {
        gameUtils.printGameState()
        println("Enter your guess (letter or the whole word):")
        return readLine()?.takeIf { guess -> guess.isNotBlank() && guess matches ALPHANUMERIC_REG }?.toUpperCase()
    }

    private fun checkGameFinished(): Boolean {
        val won = gameUtils.checkGameWon()
        val lost = gameUtils.checkGameLost()
        return when {
            won -> {
                println("Win! Word complete. The answer was '${gameUtils.getWord()}'.")
                true
            }
            lost -> {
                println("Lost! Guessed wrong too many times. The answer was '${gameUtils.getWord()}'.")
                true
            }
            else -> false
        }
    }

    companion object {
        private const val GUESSES = 6
        private val ALPHANUMERIC_REG = Regex("[\\w ]+")
        private val logger = LoggerFactory.getLogger(Hangman::class.java)
    }
}
