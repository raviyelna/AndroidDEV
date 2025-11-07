package com.example.bt1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.statusBars())

        val editText = findViewById<EditText>(R.id.editTextText)
        val textOutput = findViewById<TextView>(R.id.textOutput)
        val separateButton = findViewById<Button>(R.id.button)
        val reverseButton = findViewById<Button>(R.id.button2)

        reverseButton.setOnClickListener {
            val inputText = editText.text.toString()
            if (inputText.isNotBlank()){
                val reversedString = reverseString(inputText)
                textOutput.text = reversedString
            } else {
                Toast.makeText(this, "Vui lòng nhập chuỗi cần đảo!", Toast.LENGTH_SHORT).show()
            }
        }

        separateButton.setOnClickListener {
            val inputText = editText.text.toString()
            if (inputText.isNotBlank()) {
                try {
                    val numbers = ArrayList(inputText.split(" ").map { it.toInt() })
                    val (oddNumbers, evenNumbers) = separateNumbers(numbers)
                    textOutput.text = "Số lẻ: $oddNumbers\nSố chẵn: $evenNumbers"
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Vui lòng chỉ nhập số và dấu cách!", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivity2", "Invalid number format", e)
                } catch (e: Exception) {
                     Toast.makeText(this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show()
                     Log.e("MainActivity2", "An error occurred", e)
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập số!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun separateNumbers(numbers: ArrayList<Int>): Pair<List<Int>, List<Int>> {
        val oddNumbers = numbers.filter { it % 2 != 0 }
        val evenNumbers = numbers.filter { it % 2 == 0 }

        Log.d("MainActivity2", "Odd numbers: $oddNumbers")
        Log.d("MainActivity2", "Even numbers: $evenNumbers")
        return Pair(oddNumbers, evenNumbers)
    }

    private fun reverseString(string: String): String {
        if (string.isEmpty()) {
            return string
        }
        return string.reversed().uppercase()
    }
}