package com.github.gherkin70.main

import com.github.gherkin70.Hangman

fun main(args: Array<String>) {
    val wordToGuess = args.firstOrNull() ?: "HANGMAN"
    val hangman = Hangman(wordToGuess.toUpperCase())
    hangman.startGame()
}
