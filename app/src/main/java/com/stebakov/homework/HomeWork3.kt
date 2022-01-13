package com.stebakov.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.squareup.picasso.Picasso

class HomeWork3 : AppCompatActivity() {

    private val imageView by lazy { findViewById<ImageView>(R.id.imageView) }
    private val edtLink by lazy { findViewById<EditText>(R.id.edt_link) }
    private val btnLoad by lazy { findViewById<Button>(R.id.btn_load) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_work3)
        btnLoad.setOnClickListener {
            load(edtLink.text.toString())
        }
    }

    private fun load(link: String) {
        Picasso.with(this)
            .load(link)
            .fit()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(imageView)
    }
}