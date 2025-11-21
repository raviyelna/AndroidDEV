package com.example.exercise3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bt_listView = findViewById<Button>(R.id.bt_listView)
        val bt_gridView = findViewById<Button>(R.id.bt_gridView)
        val bt_recycleView = findViewById<Button>(R.id.bt_recycleView)


        bt_listView.setOnClickListener {
            val intent = Intent(this, ListViewActivity::class.java)
            startActivity(intent)
        }

        bt_gridView.setOnClickListener {
            val intent = Intent(this, GridViewActivity::class.java)
            startActivity(intent)
        }
        bt_recycleView.setOnClickListener {
            val intent = Intent(this, RecycleViewActivity::class.java)
            startActivity(intent)
        }





    }
}