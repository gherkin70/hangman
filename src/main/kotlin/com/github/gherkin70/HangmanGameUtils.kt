package com.github.gherkin70

class HangmanGameUtils(
    wordToGuess: String,
    guesses: Int
) {
    private var state: HangmanState = HangmanState(
        letters = wordToGuess.toCharArray().map { LetterToGuess(it.toUpperCase(), false) },
        remainingGuesses = guesses
    )

    fun answer(letter: Char) {
        val correct = state.isCorrect(letter)
        state = if (correct) {
            // letter is a correct guess, mark all matching letters as answered
            val answeredLetters = state.markLettersAsAnswered(letter)
            state.copy(letters = answeredLetters)
        } else {
            // incorrect guess, add letter to incorrect guesses and deduct from remaining guesses
            state.copy(
                incorrectGuesses = state.incorrectGuesses + letter,
                remainingGuesses = state.remainingGuesses - 1
            )
        }
    }

    fun printGameState() {
        println("${state.remainingGuesses} guesses left")
        println("${state.letters.map { if (it.answered) it.letter else '_' }}")
        println("Incorrect words: ${state.incorrectGuesses}")
    }

    fun checkGameWon() = state.gameWon()

    fun checkGameLost() = state.gameLost()

    fun checkDuplicateAnswer(letter: Char) = state.isDuplicateAnswer(letter)
}
