import io.kotest.assertions.withClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.io.File
import java.io.FileNotFoundException

@Suppress("unused")
class WordleTest : StringSpec({

    // isValid tests

    "accepts lowercase 5letter word" {
        isValid("grape") shouldBe true
    }

    "accepts uppercase 5 letter word" {
        isValid("HOUSE") shouldBe true
    }

    "blocks words shorter than 5 characters" {
        isValid("pie") shouldBe false
    }

    "blocks words longer than 5 characters" {
        isValid("strawberry") shouldBe false
    }

    "blocks words containing non-letter characters" {
        isValid("ab3de") shouldBe false
    }

    // readWord tests

    "reads the words from  file into a mutable list" {
        val tempFile = File.createTempFile("words", ".txt")
        tempFile.writeText(
            """
            apple
            banana
            crate
            12345
            toolongword
            """.trimIndent()
        )

        val words = readWordList(tempFile.absolutePath)

        withClue("The returned list should be mutable") {
            words.shouldBeInstanceOf<MutableList<String>>()
        }

        withClue("Only valid 5-letter words should be included") {
            words shouldContainExactly listOf("apple", "crate")
        }

        tempFile.delete()
    }

    "exception when the file does not exist" {
        shouldThrow<FileNotFoundException> {
            readWordList("no_such_file.txt")
        }
    }

    //pickRandomWord tests

    "returns a word that originally existed in the list" {
        val words = mutableListOf("alpha", "bravo", "charlie")
        val chosen = pickRandomWord(words)

        listOf("alpha", "bravo", "charlie").contains(chosen) shouldBe true
    }

    "removes the chosen word from the list" {
        val words = mutableListOf("delta", "echo", "foxtrot")
        val originalSize = words.size

        pickRandomWord(words)

        words.size shouldBe (originalSize - 1)
    }

    //evaluateGuess test

    "returns all 2s when the guess matches the target exactly" {
        evaluateGuess("apple", "apple") shouldContainExactly listOf(2, 2, 2, 2, 2)
    }

    "returns all 0s when no letters appear in the target" {
        evaluateGuess("apple", "wrong") shouldContainExactly listOf(0, 0, 0, 0, 0)
    }

})
