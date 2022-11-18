package com.backgroundtask

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognizerIntent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelLazy
import java.util.*


class MainActivity : AppCompatActivity() {

    var speak: ImageView? = null
    var conv_text: TextView? = null

    private val viewModel: MainViewModel by ViewModelLazy(
        MainViewModel::class,
        { viewModelStore },
        { defaultViewModelProviderFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speak = findViewById(R.id.iv_mic)
        conv_text = findViewById(R.id.tv_text)

        speak!!.setOnClickListener( object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent
                        = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (e: Exception ) {
                    Toast
                        .makeText(applicationContext, " " + e.message,
                            Toast.LENGTH_SHORT)
                        .show();
                }

            }

        })

//        viewModel.sampleRunBlocking()

        if (!checkAccessibilityPermission()) {
            Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT).show();
        }


    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                conv_text!!.setText(
                    Objects.requireNonNull(result)!![0]
                )
            }
        }
    }

    companion object {
        const val REQUEST_CODE_SPEECH_INPUT = 1
    }

    fun checkAccessibilityPermission(): Boolean {
        var accessEnabled = 0
        try {
            accessEnabled =
                Settings.Secure.getInt(this.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        return if (accessEnabled == 0) {
            // if not construct intent to request permission
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            // request permission via start activity for result
            startActivity(intent)
            false
        } else {
            true
        }
    }
}