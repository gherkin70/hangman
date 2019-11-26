package com.github.gherkin70.randomwordgenerator.model

class PhraseData(
    val data: List<Phrase>
) {
    fun phrases() = data.map { it.phrase }.filter {
        // filter is made to remove phrases with punctuation such as apostrophes
        it matches ALPHANUMERIC_REG
    }

    companion object {
        private val ALPHANUMERIC_REG = Regex("[\\w ]+")
    }
}

class Phrase(
    val phrase: String
)
