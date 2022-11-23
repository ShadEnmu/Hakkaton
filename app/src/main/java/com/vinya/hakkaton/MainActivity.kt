package com.vinya.hakkaton
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val hello : String = "Hello, Ваниа"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView : TextView = findViewById(R.id.textview)
        textView.text = hello
       // VADIM LOHasd asd8564652198
    }
}
