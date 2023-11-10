package net.azarquiel.traductor

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.traductor.adapter.WordAdapter
import net.azarquiel.traductor.model.Word
import net.azarquiel.traductor.util.Util

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: WordAdapter
    private lateinit var words: java.util.ArrayList<Word>
    private lateinit var englishSH: SharedPreferences
    private lateinit var spanishSH: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadResources()

        getAllWords()

        initRv()
        adapter.setWords(words)
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
        adapter = WordAdapter(this, R.layout.row_word)
        rvWords.adapter = adapter
        rvWords.layoutManager = LinearLayoutManager(this)
    }
}
