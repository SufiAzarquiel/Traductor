package net.azarquiel.traductor

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import net.azarquiel.traductor.model.Word
import net.azarquiel.traductor.util.Util

class MainActivity : AppCompatActivity() {
    private lateinit var words: java.util.ArrayList<Word>
    private lateinit var englishSH: SharedPreferences
    private lateinit var spanishSH: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadResources()

        getAllWords()

        for (word in words) {
            Log.d("sufiDev", "onCreate: ${word.spWord} - ${word.enWord}")
        }
    }

    private fun loadResources() {
        // XML files
        // - Assets
        // -- Words in spanish
        Util.inyecta(this, "espanol.txt")
        // -- Words in english
        Util.inyecta(this, "ingles.txt")
        // - Shared Preferences
        // -- Words in spanish
        spanishSH = getSharedPreferences("espanol", Context.MODE_PRIVATE)
        // -- Words in english
        englishSH = getSharedPreferences("ingles", Context.MODE_PRIVATE)
    }

    private fun getAllWords() {
        words = ArrayList()
        spanishSH.all.forEach {
            var enWord = englishSH.getString(it.key, null)
            var word = Word(it.key, it.value.toString(), enWord.toString())
            words.add(word)
        }
    }
}
