package com.example.kbtech.Fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.kbtech.R
import com.example.kbtech.Roomdb.AppDatabase
import com.example.kbtech.Roomdb.User
import com.example.kbtech.ShowDataActivity
import com.example.kbtech.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.io.IOException


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.showBtn.setOnClickListener {
            startActivity(Intent(context, ShowDataActivity::class.java))
        }
        Picasso.get().load("https://images.rawpixel.com/image_800/cHJpdmF0ZS9sci9pbWFnZXMvd2Vic2l0ZS8yMDIyLTA0L2pvYjY4Ni0yNTMteC5qcGc.jpg").into(binding.imaLogo)

        binding.sumbitBtn.setOnClickListener {
            binding.progessBar.visibility = View.VISIBLE
            SaveData()

        }
        return binding.root
    }

    private fun SaveData() {
        val name = binding.nameEdit.text.toString().trim()
        val address = binding.addresEdit.text.toString().trim()
        val hobbies = binding.hobbies.text.toString().trim()

        // Validate input
        if (name.isEmpty() || address.isEmpty() || hobbies.isEmpty()) {
            // Show an error message
            binding.errorText.visibility = View.VISIBLE
            binding.progessBar.visibility = View.GONE
            binding.errorText.text = "All fields must be filled out"
            return
        } else {
            binding.errorText.visibility = View.GONE
        }
        binding.nameEdit.setText("")
        binding.addresEdit.setText("")
        binding.hobbies.setText("")
        val answer = User(name = name, address = address, hobbies = hobbies)

        lifecycleScope.launch {
            binding.progessBar.visibility = View.VISIBLE
            try {
                val db = context?.let { AppDatabase.getDatabase(it) }
                db?.answerDao()?.insertAnswer(answer)
                Log.d("TAG", "SaveData: === "+db?.answerDao()?.getAllAnswers())

                // Hide the progress bar and start the new activity
                binding.progessBar.visibility = View.GONE
                startActivity(Intent(context, ShowDataActivity::class.java))

            } catch (e: IOException) {
                e.printStackTrace()
                binding.progessBar.visibility = View.GONE
                // Show an error message
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = "Failed to save data. Please try again."
            }
        }
    }
}