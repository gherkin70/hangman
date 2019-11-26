package com.github.gherkin70.randomwordgenerator.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class WordDataTest {
    private val testClass = WordData(
        listOf(
            Word(WordValue("hello")),
            Word(WordValue("yes")),
            Word(WordValue("there"))
        )
    )

    @Test
    fun `should return words of length`() {
        assertEquals(2, testClass.wordsOfLength(5).size)
        assertEquals(1, testClass.wordsOfLength(3).size)
        assertTrue(testClass.wordsOfLength(4).isEmpty())
    }
}
