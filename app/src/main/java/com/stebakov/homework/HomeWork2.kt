package com.stebakov.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

const val COUNTER = "COUNTER"

class HomeWork2 : AppCompatActivity() {

    private var mCount = 0
    private val tvCount by lazy {findViewById<TextView>(R.id.tv_count)}
    private val btnCount by lazy {findViewById<Button>(R.id.btn_count)}
    private val btnToast by lazy {findViewById<Button>(R.id.btn_toast)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_work2)
        savedInstanceState?.let {
            mCount = it.getInt(COUNTER)
            setCount(tvCount)
        }
        setClickListeners()
    }

    private fun setClickListeners() {
        btnToast.setOnClickListener {
            showToast()
        }

        btnCount.setOnClickListener {
            countUp()
            setCount(tvCount)
        }
    }

    private fun showToast() {
        Toast.makeText(this, R.string.toast_text, Toast.LENGTH_SHORT).show()
    }

    private fun countUp() {
        mCount++
    }

    private fun setCount(view: TextView) {
        view.text = mCount.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNTER, mCount)
    }
}