package com.github.gherkin70

data class HangmanState(
    val letters: List<LetterToGuess>,
    val incorrectGuesses: Set<Char> = setOf(),
    val remainingGuesses: Int
) {
    fun isDuplicateAnswer(letter: Char) = letter in incorrectGuesses + letters.filter { it.answered }.map { it.letter }

    fun isCorrect(letter: Char) = letter in letters.map { it.letter }

    fun markLettersAsAnswered(letter: Char) = letters.map { it.copy(answered = it.answered || it.letter == letter) }

    fun gameWon() = letters.all { it.answered }

    fun gameLost() = remainingGuesses == 0
}

data class LetterToGuess(
    val letter: Char,
    val answered: Boolean
)
