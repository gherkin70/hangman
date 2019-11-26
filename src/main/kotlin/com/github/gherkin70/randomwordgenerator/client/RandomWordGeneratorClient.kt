package com.github.gherkin70.randomwordgenerator.client

import com.github.gherkin70.randomwordgenerator.model.PhraseData
import com.github.gherkin70.randomwordgenerator.model.WordData
import com.google.gson.Gson
import org.json.JSONObject
import org.slf4j.LoggerFactory

class RandomWordGeneratorClient {
    fun getRandomWord(wordLength: Int): String {
        if (wordLength <= 1) {
            throw IllegalArgumentException("Word length has to be at least 2")
        }
        logger.info("Retrieving random word of length $wordLength")
        val responseData = makeGetRequest(WORD_URL)
        val data = Gson().fromJson(responseData.toString(), WordData::class.java)
        val words = data.wordsOfLength(wordLength)
        if (words.isEmpty()) {
            throw IllegalArgumentException(
                "There are no words available with the length of $wordLength. Consider a shorter word length"
            )
        }
        val word = words.random()
        logger.info("Returning random word: $word")
        return word
    }

    fun getRandomPhrase(): String {
        val responseData = makeGetRequest(PHRASE_URL)
        val data = Gson().fromJson(responseData.toString(), PhraseData::class.java)
        val phrase = data.phrases().random()
        logger.info("Returning random phrase: $phrase")
        return phrase
    }

    private fun makeGetRequest(url: String): JSONObject {
        logger.info("Retrieving words from URL: $url")
        val response = khttp.get(url = url, headers = mapOf("Accept" to "application/json"))
        if (response.statusCode == 200) {
            return response.jsonObject
        } else {
            logger.error("Error calling random words api. Status code: ${response.statusCode}")
            throw IllegalStateException("Error calling random words api.")
        }
    }

    companion object {
        private const val WORD_URL = "https://randomwordgenerator.com/json/words_ws.json"
        private const val PHRASE_URL = "https://randomwordgenerator.com/json/phrases.json"
        private val logger = LoggerFactory.getLogger(RandomWordGeneratorClient::class.java)
    }
}
