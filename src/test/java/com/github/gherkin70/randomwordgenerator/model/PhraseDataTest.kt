package com.github.gherkin70.randomwordgenerator.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PhraseDataTest {
    private val testClass = PhraseData(
        listOf(
            Phrase("include this phrase"),
            Phrase("don't include this phrase")
        )
    )

    @Test
    fun `should filter the word not matching regex`() {
        assertEquals(1, testClass.phrases().size)
    }
}
