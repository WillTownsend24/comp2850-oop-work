import java.io.File
import kotlin.random.Random

fun isValid(word: String): Boolean {
    return word.length == 5 && word.all { it.isLetter() }
}

fun readWordList(filename: String): MutableList<String> {
    val words = mutableListOf<String>()
    File(filename).forEachLine { line ->
        val w = line.trim()
        if (isValid(w)) {
            words.add(w.lowercase())
        }
    }
    return words
}

fun pickRandomWord(words: MutableList<String>): String {
    if (words.isEmpty()) return ""
    val index = Random.nextInt(words.size)
    val word = words[index]
    words.removeAt(index)
    return word
}

fun obtainGuess(attempt: Int): String {
    while (true) {
        print("Attempt $attempt: ")
        val guess = readln().trim().lowercase()
        if (isValid(guess)) {
            return guess
        } else {
            println("Invalid word, please try again.")
        }
    }
}

fun evaluateGuess(guess: String, target: String): List<Int> {
    val result = mutableListOf<Int>()
    
    for (i in 0..4) {           // instead of until 5
        if (guess[i] == target[i]) {
            result.add(2)
        } else {
            var found = false
            for (j in 0..4) {  
                if (guess[i] == target[j] && i != j) {
                    found = true
                    break
                }
            }
            if (found) {
                result.add(1)
            } else {
                result.add(0)
            }
        }
    }
    return result
}

fun displayGuess(guess: String, matches: List<Int>) {
    var output = ""
    for (i in 0..4) {
        if (matches[i] == 2) {
            output = output + "\u001B[42m\u001B[30m" + guess[i] + "\u001B[0m"
        } else if (matches[i] == 1) {
            output = output + "\u001B[43m\u001B[30m" + guess[i] + "\u001B[0m"
        } else {
            output = output + "?"
        }
    }
    println(output)
}
