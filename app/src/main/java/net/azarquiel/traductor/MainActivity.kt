package net.azarquiel.traductor

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.traductor.adapter.WordAdapter
import net.azarquiel.traductor.model.Word
import net.azarquiel.traductor.util.Util
import java.util.Locale

class MainActivity : AppCompatActivity(), WordAdapter.TextToSpeechListener {
    private lateinit var tts: TextToSpeech
    private lateinit var adapter: WordAdapter
    private lateinit var words: java.util.ArrayList<Word>
    private lateinit var englishSH: SharedPreferences
    private lateinit var spanishSH: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createTTS()
        loadResources()

        getAllWords()

        initRv()
        adapter.setWords(words)
    }

    private fun createTTS() {
        tts = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                tts.language = Locale.getDefault()
            }
        }
    }

    private fun loadResources() {
        // XML files

        // - Assets
        // -- Words in spanish
        Util.inyecta(this, "espanol.xml")
        // -- Words in english
        Util.inyecta(this, "ingles.xml")

        // - Shared Preferences
        // -- Words in spanish
        spanishSH = getSharedPreferences("espanol", Context.MODE_PRIVATE)
        // -- Words in english
        englishSH = getSharedPreferences("ingles", Context.MODE_PRIVATE)
    }

    private fun getAllWords() {
        words = ArrayList()
        spanishSH.all.forEach {
            val enWord = englishSH.getString(it.key, null)
            val word = Word(it.key, it.value.toString(), enWord.toString())
            words.add(word)
        }
    }

    private fun initRv() {
        val rvWords = findViewById<RecyclerView>(R.id.rvWords)
        adapter = WordAdapter(this, R.layout.row_word, this)
        rvWords.adapter = adapter
        rvWords.layoutManager = LinearLayoutManager(this)
    }

    override fun onTextToSpeechRequested(text: String, language: String) {
        if(language == "sp"){
            ttsReadText(text, "es")
        }
        else if (language == "en"){
            ttsReadText(text, "en")
        }
    }

    private fun ttsReadText(text: String, language: String) {
        tts.language = Locale(language)
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}
