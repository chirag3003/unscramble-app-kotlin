package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var _score = MutableLiveData(0)
    private var _currentWordCount = MutableLiveData(0)
    private val _currentScrambledWord = MutableLiveData<String>()
    private var wordList:MutableList<String> = mutableListOf()
    private lateinit var currentWord:String

    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    val score: LiveData<Int>
        get() = _score

    init{
        genNextWord()
    }

    private fun genNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(currentWord,false)){
            tempWord.shuffle()
        }
        if(wordList.contains(currentWord)){
            genNextWord()
        }else{
            Log.d("WORD",currentWord)
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordList.add(currentWord)
        }

    }

    fun nextWord(): Boolean{
        if(_currentWordCount.value !!< MAX_NO_OF_WORDS){
            genNextWord()
            return true
        }
        return false
    }

    private fun increaseScore(){
        Log.d("SCORE", _score.value.toString())
        _score.value = _score.value!! + SCORE_INCREASE
    }

    fun isUserWordCorrect(word: String):Boolean{
        return if(word == currentWord){
            Log.d("SCORE","yes")
            increaseScore()
            true
        } else
            false
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        genNextWord()
    }

}