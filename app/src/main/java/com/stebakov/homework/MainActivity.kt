package com.stebakov.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val btnHw2 by lazy { findViewById<Button>(R.id.btn_hw2) }
    private val btnHw3 by lazy { findViewById<Button>(R.id.btn_hw3) }
    private val btnHw4 by lazy { findViewById<Button>(R.id.btn_hw4) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickListeners()
    }

    private fun setClickListeners() {
        btnHw4.setOnClickListener {
            startActivity(Intent(this, HomeWork4::class.java))
        }
        btnHw3.setOnClickListener {
            startActivity(Intent(this, HomeWork3::class.java))
        }

        btnHw2.setOnClickListener {
            startActivity(Intent(this, HomeWork2::class.java))
        }
    }
}