package com.example.kbtech

import AnswerAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kbtech.Roomdb.AppDatabase
import com.example.kbtech.databinding.ActivityShowDataBinding
import kotlinx.coroutines.launch

class ShowDataActivity : AppCompatActivity() {

    private lateinit var adapter: AnswerAdapter

    lateinit var binding: ActivityShowDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityShowDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.lavender)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle the navigation icon click event
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val answers = db?.answerDao()?.getAllAnswers()?.toMutableList() ?: mutableListOf() // Convert to MutableList

            Log.d("TAG", "Retrieved answers: $answers") // Log the data retrieved

            if (answers.isNotEmpty()) {
                adapter = AnswerAdapter(this@ShowDataActivity, answers, db.answerDao()) // Pass AnswerDao as well
                binding.recyclerView.adapter = adapter
            } else {
                Log.d("TAG", "No data found in the database")
                // Show a message to the user or handle the empty data case
            }
        }
    }

}