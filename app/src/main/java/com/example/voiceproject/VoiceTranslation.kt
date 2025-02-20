package com.example.voiceproject

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

import java.util.Locale

@Suppress("DEPRECATION")
class VoiceTranslation : AppCompatActivity() {
    private lateinit var toolbarvt: Toolbar
    private lateinit var textbox1: TextView
    private lateinit var textbox2: TextView
    private lateinit var btndelete: Button
    private lateinit var btncopy: Button
    private lateinit var btnpaste: Button
    private lateinit var btnmic: Button
    private lateinit var spiner: Spinner
    private lateinit var spiner2: Spinner
    private lateinit var share: Button
    private lateinit var btnspeak: Button
    private lateinit var btntranslate: Button
    private lateinit var clipBoard: ClipboardManager
    private lateinit var flagimage: ImageView
    private lateinit var flagimage2: ImageView
    private lateinit var tts: TextToSpeech


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_voice_translation)

        clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        toolbarvt = findViewById(R.id.toolbarvt)
        btnmic = findViewById(R.id.btnmic)
        spiner = findViewById(R.id.spiner)
        spiner2 = findViewById(R.id.spiner2)
        share = findViewById(R.id.share)
        flagimage = findViewById(R.id.flagimage)
        flagimage2 = findViewById(R.id.flagimage2)
        textbox1 = findViewById(R.id.textbox1)
        textbox2 = findViewById(R.id.textbox2)
        btndelete = findViewById(R.id.btndelete)
        btncopy = findViewById(R.id.btncopy)
        btntranslate = findViewById(R.id.btntranslate)
        btnpaste = findViewById(R.id.btnpaste)
        btnspeak = findViewById(R.id.btnspeak)
        toolbarvt.setNavigationOnClickListener()
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnspeak.setOnClickListener {
            tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val selectedLanguage = spiner2.selectedItem.toString()
                    val language = when (selectedLanguage) {
                        "English" -> Locale.ENGLISH
                        "Chinese" -> Locale.CHINESE
                        "Japanese" -> Locale.JAPANESE
                        "Hindi" -> Locale("hi")
                        "Urdu" -> Locale("ur")
                        else -> Locale.getDefault()
                    }
                    val result = tts.setLanguage(language)
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
                    } else {
                        tts.setSpeechRate(1.0f)
                        tts.speak(textbox2.text.toString(), TextToSpeech.QUEUE_ADD, null, null)
                    }
                } else {
                    Toast.makeText(this, "Text-to-Speech initialization failed", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btndelete.setOnClickListener {
            textbox1.setText("")
            textbox2.setText("")
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
                    ClipDescription.MIMETYPE_TEXT_PLAIN
                ) == true
            ) {
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
                putExtra(Intent.EXTRA_TEXT, textbox2.text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        val languages = listOf("English", "Hindi", "Urdu", "Chinese", "Japanese")
        var selectedLanguageCode = Locale.getDefault().toString()

        spiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = languages[position]
                selectedLanguageCode = when (selectedLanguage) {
                    "English" -> {
                        flagimage.setImageResource(R.drawable.usa)

                        "en-US"
                    }

                    "Hindi" -> {
                        flagimage.setImageResource(R.drawable.ind)
                        "hi-IN"
                    }

                    "Urdu" -> {
                        flagimage.setImageResource(R.drawable.pak)
                        "ur-PK"
                    }

                    "Chinese" -> {
                        flagimage.setImageResource(R.drawable.china)
                        "zh-CN"
                    }
                    "Japanese" -> {
                        flagimage.setImageResource(R.drawable.japan)
                        "ja-JP"
                    }

                    else -> Locale.getDefault().toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        spiner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = languages[position]
                selectedLanguageCode = when (selectedLanguage) {
                    "English" -> {
                        flagimage2.setImageResource(R.drawable.usa)

                        "en-US"
                    }

                    "Hindi" -> {
                        flagimage2.setImageResource(R.drawable.ind)
                        "hi-IN"
                    }

                    "Urdu" -> {
                        flagimage2.setImageResource(R.drawable.pak)
                        "ur-PK"
                    }

                    "Chinese" -> {
                        flagimage2.setImageResource(R.drawable.china)
                        "zh-CN"
                    }

                    "Japanese" -> {
                        flagimage2.setImageResource(R.drawable.japan)
                        "ja-JP"
                    }

                    else -> Locale.getDefault().toString()
                }
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btntranslate.setOnClickListener {
            val inputText = textbox1.text.toString()
            if (inputText.isNotEmpty()) {
                val sourceLanguageCode = when (spiner.selectedItem.toString()) {
                    "Urdu" -> TranslateLanguage.URDU
                    "Hindi" -> TranslateLanguage.HINDI
                    "Chinese" -> TranslateLanguage.CHINESE
                    "Japanese" -> TranslateLanguage.JAPANESE
                    "English" -> TranslateLanguage.ENGLISH
                    else -> Locale.getDefault().toString()
                }

                val targetLanguageCode = when (spiner2.selectedItem.toString()) {
                    "Urdu" -> TranslateLanguage.URDU
                    "Hindi" -> TranslateLanguage.HINDI
                    "Chinese" -> TranslateLanguage.CHINESE
                    "Japanese" -> TranslateLanguage.JAPANESE
                    "English" -> TranslateLanguage.ENGLISH
                    else -> Locale.getDefault().toString()
                }

                translateText(inputText,sourceLanguageCode, targetLanguageCode) { translatedText ->
                    textbox2.text = translatedText
                }
            } else {
                Toast.makeText(this, "No text to translate", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun translateText(inputText: String, sourceLanguageCode: String, targetLanguageCode: String, callback: (String) -> Unit
    ) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguageCode)
            .setTargetLanguage(targetLanguageCode)
            .build()

        val translator = Translation.getClient(options)
        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(inputText)
                    .addOnSuccessListener { translatedText ->
                        callback(translatedText)
                    }
                    .addOnFailureListener { exception ->
                        callback("Translation failed: ${exception.localizedMessage}")
                    }
            }
            .addOnFailureListener { exception ->
                callback("Model download failed: ${exception.localizedMessage}")
            }
    }

    val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == RESULT_OK) {
                val results = result.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                ) as ArrayList<String>
                val resultString = results.joinToString(" ")
                textbox1.setText(resultString)
            }
        }
    }