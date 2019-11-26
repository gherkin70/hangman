package com.github.gherkin70.randomwordgenerator.model

class WordData(
    val data: List<Word>
) {
    fun wordsOfLength(wordLength: Int) = data.map { it.word.value }.filter { it.length == wordLength }
}

class Word(
    val word: WordValue
)

class WordValue(
    val value: String
)
