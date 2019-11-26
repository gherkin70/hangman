package com.github.gherkin70.hangman

data class HangmanState(
    val wordToGuess: String,
    val incorrectGuesses: Set<Char> = setOf(),
    val remainingGuesses: Int,
    val words: List<List<LetterToGuess>> = wordToGuess.split(" ").map { word ->
        word.toCharArray().map { letter ->
            LetterToGuess(letter, false)
        }
    }
) {
    private val lettersFlat = words.flatten()

    fun isDuplicateLetter(letter: Char) =
        letter in incorrectGuesses + lettersFlat.filter { it.answered }.map { it.letter }

    fun correctWord(word: String) = word == this.wordToGuess

    fun correctLetter(letter: Char) = letter in lettersFlat.map { it.letter }

    fun markLetterAsAnswered(letter: Char) = words.map { word ->
        word.map { it.copy(answered = it.answered || it.letter == letter) }
    }

    fun markAllLettersAsAnswered() = words.map { word ->
        word.map { it.copy(answered = true) }
    }

    fun gameWon() = lettersFlat.all { it.answered }

    fun gameLost() = remainingGuesses == 0
}

data class LetterToGuess(
    val letter: Char,
    val answered: Boolean
)
