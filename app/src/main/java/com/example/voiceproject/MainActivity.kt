package com.example.voiceproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var voiceTyping: Button
    private lateinit var voiceTranslation: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
         installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        voiceTyping=findViewById(R.id.voiceTyping)
        voiceTranslation=findViewById(R.id.voicetranslation)

        voiceTyping.setOnClickListener()
        {
            val intent = Intent(this, VoiceTyping::class.java)
            startActivity(intent)
        }
        voiceTranslation.setOnClickListener{
            var intent= Intent(this,VoiceTranslation::class.java)
            startActivity(intent)
        }

    }
}