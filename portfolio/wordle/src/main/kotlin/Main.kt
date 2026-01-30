fun main() {
    val filename = "data/words.txt"  // filename variable
    var wordList: MutableList<String> = readWordList(filename)
    
    if (wordList.size == 0) {       
        println("No words found in file!")
        return
    }
    
    val secretWord = pickRandomWord(wordList)
    var attempts = 0
    var gameWon = false
    
    while (attempts < 6) {          
        attempts = attempts + 1
        println("Guess number " + attempts + ": ")
        
        val playerGuess = obtainGuess(attempts)
        
        val feedback = evaluateGuess(playerGuess, secretWord)
        displayGuess(playerGuess, feedback)
        
        var allCorrect = true
        for (score in feedback) {    // check it == 2 }
            if (score != 2) {
                allCorrect = false
                break
            }
        }
        
        if (allCorrect) {
            println("Well done! You got it in " + attempts + " tries!")
            gameWon = true
            break
        }
    }
    
    if (!gameWon) {
        println("Sorry, no more guesses. The word was: " + secretWord)
    }
}