package com.example.voiceproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.Locale

class VoiceTyping : AppCompatActivity() {

    private lateinit var toolbarvt: Toolbar
    private lateinit var  textbox1: TextView
    private lateinit var  btndelete: Button
    private lateinit var  btncopy: Button
    private lateinit var  btnpaste: Button
    private lateinit var  btnmic : Button
    private lateinit var  spiner: Spinner
    private lateinit var  share : Button
    private lateinit var clipBoard:ClipboardManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_voice_typing)

        clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        toolbarvt=findViewById(R.id.toolbarvt)
        btnmic=findViewById(R.id.btnmic)
        spiner=findViewById(R.id.spiner)
        share = findViewById(R.id.share)


        toolbarvt.setNavigationOnClickListener()
        {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        textbox1 =findViewById(R.id.textbox1)
        btndelete =findViewById(R.id.btndelete)
        btncopy=findViewById(R.id.btncopy)
        btnpaste=findViewById(R.id.btnpaste)

        btndelete.setOnClickListener(){
          textbox1.setText("")
            previousresult = ""
            savePreferences()

        }
        btncopy.setOnClickListener {

            val copytext = textbox1.text.toString()
            if (copytext.isNotEmpty()) {
                val clip = ClipData.newPlainText("copied Text", copytext)
                clipBoard.setPrimaryClip(clip)
                Toast.makeText(this, "Copied Text", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No text to copy", Toast.LENGTH_SHORT).show()
            }
        }
        btnpaste.setOnClickListener()
        {
            if (clipBoard.hasPrimaryClip() && clipBoard.primaryClipDescription?.hasMimeType(
                    ClipDescription.MIMETYPE_TEXT_PLAIN) == true) {
                val item = clipBoard.primaryClip?.getItemAt(0)
                val pastedText = item?.text.toString()
                textbox1.setText(pastedText)
                Toast.makeText(this, "Text Pasted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No text to paste", Toast.LENGTH_SHORT).show()
            }
        }
        share.setOnClickListener()
        {

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textbox1.text)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        val languages = listOf("English", "Hindi", "Urdu","Chinese","Japanese")
        var selectedLanguageCode = Locale.getDefault().toString()

        loadPreferences()

        spiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = languages[position]
                selectedLanguageCode = when (selectedLanguage) {
                    "English" -> "en-US"
                    "Hindi" -> "hi-IN"
                    "Urdu" -> "ur-PK"
                    "Chinese" -> "zh-CN"
                    "Japanese" -> "ja-JP"

                    else -> Locale.getDefault().toString()
                }
                savePreferences()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        btnmic.setOnClickListener() {
                    textbox1.text = ""
                    try {
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                        intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE,
                            selectedLanguageCode
                        )
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something")
                        result.launch(intent)
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                }


    }
    var previousresult = ""

    val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val results = result.data?.getStringArrayListExtra(
                RecognizerIntent.EXTRA_RESULTS
            ) as ArrayList<String>

            previousresult += " " + results[0]
            textbox1.setText(previousresult)
            savePreferences()
        }
    }
    private fun savePreferences() {
        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE).edit()
        sharedPref.putString("spinner", spiner.selectedItem.toString())
        sharedPref.putString("result", previousresult)
        sharedPref.apply()
    }

    private fun loadPreferences() {
        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        val storedSpinner = sharedPref.getString("spinner", spiner.selectedItem.toString())
        val storedResult = sharedPref.getString("result", "")

        textbox1.text = storedResult
        previousresult = storedResult ?: ""
        spiner.setSelection(getSpinnerIndex(spiner, storedSpinner ?: spiner.selectedItem.toString()))
    }
    private fun getSpinnerIndex(spiner: Spinner, value: String): Int {
        for (i in 0 until spiner.count) {
            if (spiner.getItemAtPosition(i).toString() == value) {
                return i
            }
        }
        return 0
    }

}